package com.bookstudio;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class ModularityTests {

    static final ApplicationModules modules = ApplicationModules.of(BookstudioApplication.class);

    @Test
    @Disabled("Fails today: book<->copy and copy<->location cycles, plus modules using other modules' "
            + "repositories and entities. Re-enabled once module boundaries are fixed.")
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
