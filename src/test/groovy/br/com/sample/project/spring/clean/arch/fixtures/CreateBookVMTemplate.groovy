package br.com.sample.project.spring.clean.arch.fixtures

import br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM
import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import br.com.six2six.fixturefactory.loader.TemplateLoader

import static br.com.sample.project.spring.clean.arch.endpoint.entity.CreateBookVM.Fields.*

class CreateBookVMTemplate implements TemplateLoader {

    public static final String VALID_BOOK_VM = "Valid book vm"
    
    @Override
    void load() {
        Fixture.of(CreateBookVM).addTemplate(VALID_BOOK_VM, new Rule() {
            {
                add(author, "author test")
                add(title, "title test")
                add(pages, 1000)
            }
        })
    }
}
