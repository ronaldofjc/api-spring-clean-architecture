package br.com.sample.project.spring.clean.arch.fixtures;

import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM;

public class CreateBookVMFixture {

    public static CreateBookVM gimmeBasicCreateBookVM() {
        return CreateBookVM.builder()
                .title("Book title test")
                .author("Author test")
                .pages(100)
                .build();
    }
}
