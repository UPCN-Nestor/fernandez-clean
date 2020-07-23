package com.ofv.ofv;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.ofv.ofv");

        noClasses()
            .that()
                .resideInAnyPackage("com.ofv.ofv.service..")
            .or()
                .resideInAnyPackage("com.ofv.ofv.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.ofv.ofv.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
