package com.bookstudio;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * Fails the build when a module reaches into another module's internals
 * (anything outside its root package) or when modules depend on each other
 * in a cycle.
 */
class ModularityTests {

    static final ApplicationModules modules = ApplicationModules.of(BookstudioApplication.class);

    @Test
    void verifiesModularStructure() {
        modules.verify();
    }

    @Test
    void writesDocumentation() {
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml()
                .writeModuleCanvases();
    }
}
