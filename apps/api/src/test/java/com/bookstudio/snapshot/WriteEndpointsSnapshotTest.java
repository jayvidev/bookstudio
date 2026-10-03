package com.bookstudio.snapshot;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.bookstudio.IntegrationTest;

/**
 * Golden-master test of create and update for every resource: POST, PUT on the
 * created id and GET of the result, each compared with a stored snapshot.
 *
 * <p>Payloads reference seed rows; {@code {placeholders}} are resolved from the
 * database so the scenario stays valid. Generated ids, codes and today's date are
 * masked before comparing. Regenerate with {@code -Dsnapshots.update=true}.
 */
@IntegrationTest
class WriteEndpointsSnapshotTest {

    private static final Path SNAPSHOTS = Path.of("src/test/resources/snapshots/write");
    private static final boolean UPDATE = Boolean.getBoolean("snapshots.update");

    @Autowired
    MockMvcTester mvc;

    @Autowired
    JdbcClient jdbc;

    static Stream<Arguments> scenarios() {
        return Stream.of(
                Arguments.of("authors", """
                        {"name": "Autor Snapshot", "nationalityId": 1, "birthDate": "1950-05-10",
                         "biography": "Bio", "status": "ACTIVO", "photoUrl": null}""", """
                        {"name": "Autor Snapshot Editado", "nationalityId": 2, "birthDate": "1951-06-11",
                         "biography": "Bio editada", "status": "INACTIVO", "photoUrl": null}"""),
                Arguments.of("books", """
                        {"title": "Libro Snapshot", "isbn": "978-0-00-000001-1", "languageId": 1, "edition": "1ra",
                         "pages": 120, "description": "Desc", "coverUrl": null, "publisherId": 1, "categoryId": 1,
                         "releaseDate": "2020-01-15", "status": "ACTIVO", "authorIds": [1, 2], "genreIds": [1, 2]}""", """
                        {"title": "Libro Snapshot Editado", "isbn": "978-0-00-000001-2", "languageId": 2, "edition": "2da",
                         "pages": 150, "description": "Desc 2", "coverUrl": null, "publisherId": 2, "categoryId": 2,
                         "releaseDate": "2021-02-16", "status": "INACTIVO", "authorIds": [3], "genreIds": [3]}"""),
                Arguments.of("categories", """
                        {"name": "Categoria Snapshot", "level": "GENERAL", "description": "Desc", "status": "ACTIVO"}""", """
                        {"name": "Categoria Snapshot Editada", "level": "SUPERIOR", "description": "Desc 2", "status": "INACTIVO"}"""),
                Arguments.of("copies", """
                        {"bookId": 1, "shelfId": 1, "barcode": "SNAP-0001", "status": "DISPONIBLE", "condition": "NUEVO"}""", """
                        {"shelfId": 2, "barcode": "SNAP-0002", "status": "MANTENIMIENTO", "condition": "REGULAR"}"""),
                Arguments.of("fines", """
                        {"loanItemId": {"loanId": {loanId}, "copyId": {loanCopyId}}, "amount": 5.50, "daysLate": 3,
                         "status": "PENDIENTE", "issuedAt": "2026-01-20"}""", """
                        {"amount": 7.00, "daysLate": 4, "status": "CONDONADO"}"""),
                Arguments.of("loans", """
                        {"readerId": 1, "observation": "Obs", "items": [
                          {"copyId": {availableCopyId}, "dueDate": "2099-01-01"}]}""", """
                        {"readerId": 2, "observation": "Obs editada", "items": [
                          {"copyId": {otherAvailableCopyId}, "dueDate": "2099-03-01", "status": "PRESTADO"}]}"""),
                Arguments.of("locations", """
                        {"name": "Sala Snapshot", "description": "Desc", "shelves": [
                          {"code": "S-01", "floor": "1", "description": "Estante 1"}]}""", """
                        {"name": "Sala Snapshot Editada", "description": "Desc 2", "shelves": [
                          {"id": {createdShelfId}, "code": "S-02", "floor": "2", "description": "Estante 2"}]}"""),
                Arguments.of("payments", """
                        {"readerId": {fineReaderId}, "amount": 10.00, "paymentDate": "2026-02-01", "method": "EFECTIVO",
                         "fineIds": [{pendingFineId}]}""", """
                        {"readerId": {fineReaderId}, "amount": 12.00, "paymentDate": "2026-02-02", "method": "TARJETA",
                         "fineIds": [{pendingFineId}]}"""),
                Arguments.of("publishers", """
                        {"name": "Editorial Snapshot", "nationalityId": 1, "foundationYear": 1990, "website": "https://snap.pe",
                         "address": "Lima", "status": "ACTIVO", "photoUrl": null, "genreIds": [1, 2]}""", """
                        {"name": "Editorial Snapshot Editada", "nationalityId": 2, "foundationYear": 1991, "website": "https://snap2.pe",
                         "address": "Cusco", "status": "INACTIVO", "photoUrl": null, "genreIds": [3]}"""),
                Arguments.of("readers", """
                        {"dni": "99999901", "firstName": "Snap", "lastName": "Shot", "address": "Lima", "phone": "999999901",
                         "email": "snap@test.pe", "birthDate": "2000-01-01", "gender": "FEMENINO", "type": "ESTUDIANTE",
                         "status": "ACTIVO"}""", """
                        {"dni": "99999902", "firstName": "Snap2", "lastName": "Shot2", "address": "Cusco", "phone": "999999902",
                         "email": "snap2@test.pe", "birthDate": "2001-02-02", "gender": "MASCULINO", "type": "DOCENTE",
                         "status": "SUSPENDIDO"}"""),
                Arguments.of("reservations", """
                        {"readerId": 1, "copyId": {availableCopyId}, "reservationDate": "2026-03-01", "status": "PENDIENTE"}""", """
                        {"readerId": 2, "copyId": {otherAvailableCopyId}, "reservationDate": "2026-03-02", "status": "CANCELADA"}"""),
                Arguments.of("roles", """
                        {"name": "Rol Snapshot", "description": "Desc", "permissionIds": [1, 2]}""", """
                        {"name": "Rol Snapshot Editado", "description": "Desc 2", "permissionIds": [3]}"""),
                Arguments.of("workers", """
                        {"username": "snapshot", "email": "snapshot@bookstudio.pe", "firstName": "Snap", "lastName": "Shot",
                         "password": "Secret123!", "roleId": 1, "profilePhotoUrl": null, "status": "ACTIVO"}""", """
                        {"firstName": "Snap2", "lastName": "Shot2", "roleId": 2, "profilePhotoUrl": null, "status": "SUSPENDIDO"}"""));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("scenarios")
    void createUpdateAndRead(String resource, String createJson, String updateJson) throws Exception {
        Map<String, Object> refs = new HashMap<>(seedReferences());

        MvcTestResult created = mvc.post().uri("/" + resource)
                .contentType(MediaType.APPLICATION_JSON).content(resolve(createJson, refs)).exchange();
        assertThat(created).hasStatus(HttpStatus.CREATED);
        JSONObject createdData = new JSONObject(body(created)).getJSONObject("data");
        Object id = createdData.get("id");
        if (resource.equals("locations")) {
            refs.put("createdShelfId", jdbc.sql("SELECT id FROM shelves WHERE location_id = :id")
                    .param("id", id).query(Long.class).single());
        }

        MvcTestResult updated = mvc.put().uri("/" + resource + "/" + id)
                .contentType(MediaType.APPLICATION_JSON).content(resolve(updateJson, refs)).exchange();
        assertThat(updated).hasStatusOk();

        MvcTestResult read = mvc.get().uri("/" + resource + "/" + id).exchange();
        assertThat(read).hasStatusOk();

        compare(resource + "_create", normalize(body(created), id));
        compare(resource + "_update", normalize(body(updated), id));
        compare(resource + "_read", normalize(body(read), id));
    }

    private Map<String, Object> seedReferences() {
        var available = jdbc.sql("SELECT id FROM copies WHERE status = 'DISPONIBLE' ORDER BY id LIMIT 2")
                .query(Long.class).list();
        var loanItem = jdbc.sql("SELECT loan_id, copy_id FROM loan_items ORDER BY loan_id, copy_id LIMIT 1")
                .query().singleRow();
        var pendingFine = jdbc.sql("""
                SELECT f.id, l.reader_id FROM fines f JOIN loans l ON l.id = f.loan_id
                WHERE f.status = 'PENDIENTE' ORDER BY f.id LIMIT 1""")
                .query().singleRow();
        return Map.of(
                "availableCopyId", available.get(0),
                "otherAvailableCopyId", available.get(1),
                "loanId", loanItem.get("loan_id"),
                "loanCopyId", loanItem.get("copy_id"),
                "pendingFineId", pendingFine.get("id"),
                "fineReaderId", pendingFine.get("reader_id"));
    }

    private static String resolve(String template, Map<String, Object> refs) {
        String json = template;
        for (var ref : refs.entrySet()) {
            json = json.replace("{" + ref.getKey() + "}", String.valueOf(ref.getValue()));
        }
        return json;
    }

    private static String body(MvcTestResult result) throws Exception {
        return result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    /**
     * Masks what changes between runs: response timestamp, the generated id and
     * code of the created resource, ids of nested rows created with it, and today.
     */
    private static String normalize(String json, Object createdId) throws Exception {
        JSONObject body = new JSONObject(json);
        body.remove("timestamp");
        JSONObject data = body.getJSONObject("data");
        if (String.valueOf(data.get("id")).equals(String.valueOf(createdId))) {
            data.put("id", "<created-id>");
        }
        if (data.has("code") && !data.isNull("code")) {
            data.put("code", "<generated-code>");
        }
        if (data.has("shelves")) {
            JSONArray shelves = data.getJSONArray("shelves");
            for (int i = 0; i < shelves.length(); i++) {
                shelves.getJSONObject(i).put("id", "<created-id>");
            }
        }
        return body.toString()
                .replace("\"" + LocalDate.now() + "\"", "\"<today>\"")
                .replaceAll("\"(LEC|EJE)-\\d{4}-", "\"$1-YYYY-");
    }

    private static void compare(String name, String actual) throws Exception {
        Path file = SNAPSHOTS.resolve(name + ".json");
        if (UPDATE || Files.notExists(file)) {
            assertThat(UPDATE).as("Missing snapshot %s; run with -Dsnapshots.update=true", file).isTrue();
            Files.createDirectories(SNAPSHOTS);
            Files.writeString(file, new JSONObject(actual).toString(2) + "\n");
            return;
        }
        JSONAssert.assertEquals(Files.readString(file), actual, JSONCompareMode.STRICT);
    }
}
