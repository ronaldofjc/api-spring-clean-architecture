package br.com.sample.project.spring.clean.arch.fixtures


import br.com.sample.project.spring.clean.arch.endpoint.entity.UpdateBookVM
import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import br.com.six2six.fixturefactory.loader.TemplateLoader

import static br.com.sample.project.spring.clean.arch.endpoint.entity.UpdateBookVM.Fields.*

class UpdateBookVMTemplate implements TemplateLoader {

    public static final String VALID_UPDATE_BOOK_VM = "Valid book vm"

    @Override
    void load() {
        Fixture.of(UpdateBookVM).addTemplate(VALID_UPDATE_BOOK_VM, new Rule() {
            {
                add(id, 1L)
                add(author, "author test")
                add(title, "title test")
                add(pages, 1000)
                add(active, true)
            }
        })
    }
}
