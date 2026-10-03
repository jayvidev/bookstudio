package com.bookstudio.snapshot;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import com.bookstudio.IntegrationTest;

/**
 * Golden-master test of every read endpoint against the seed data. It pins the
 * exact JSON the frontend receives, so internal refactors cannot change it.
 *
 * <p>Snapshots live in {@code src/test/resources/snapshots}. Run with
 * {@code -Dsnapshots.update=true} to (re)write them after an intended change.
 */
@IntegrationTest
class ReadEndpointsSnapshotTest {

    private static final Path SNAPSHOTS = Path.of("src/test/resources/snapshots");
    private static final boolean UPDATE = Boolean.getBoolean("snapshots.update");

    @Autowired
    MockMvcTester mvc;

    static Stream<String> endpoints() {
        Stream<String> lists = Stream.of(
                "authors", "books", "categories", "copies", "fines", "loans", "locations",
                "payments", "publishers", "readers", "reservations", "roles", "workers")
                .flatMap(r -> Stream.of("/" + r, "/" + r + "/1", "/" + r + "/2"));
        Stream<String> filterOptions = Stream.of(
                "authors", "books", "copies", "fines", "loans", "payments", "publishers", "reservations", "workers")
                .map(r -> "/" + r + "/filter-options");
        Stream<String> selectOptions = Stream.of("authors", "books", "copies", "loans", "publishers")
                .map(r -> "/" + r + "/select-options");
        return Stream.of(lists, filterOptions, selectOptions).flatMap(s -> s);
    }

    @ParameterizedTest(name = "GET {0}")
    @MethodSource("endpoints")
    void matchesSnapshot(String uri) throws Exception {
        MvcTestResult result = mvc.get().uri(uri).exchange();
        assertThat(result).hasStatusOk();

        String actual = normalize(result.getResponse().getContentAsString(StandardCharsets.UTF_8));
        Path file = SNAPSHOTS.resolve(uri.substring(1).replace('/', '_') + ".json");

        if (UPDATE || Files.notExists(file)) {
            assertThat(UPDATE).as("Missing snapshot %s; run with -Dsnapshots.update=true", file).isTrue();
            Files.createDirectories(SNAPSHOTS);
            Files.writeString(file, new JSONObject(actual).toString(2) + "\n");
            return;
        }

        JSONAssert.assertEquals(Files.readString(file), actual, JSONCompareMode.STRICT);
    }

    /**
     * Drops the response timestamp and the year in codes generated at migration
     * time (the seed's readers and copies get the current year).
     */
    private static String normalize(String json) throws Exception {
        JSONObject body = new JSONObject(json);
        body.remove("timestamp");
        return body.toString().replaceAll("\"(LEC|EJE)-\\d{4}-", "\"$1-YYYY-");
    }
}
