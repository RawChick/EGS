/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.web.ui.mvc;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import sample.web.ui.*;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Rob Winch
 * @author Doo-Hwan Kwak
 */
@Controller
@RequestMapping("/")
public class MessageController {

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ProductCatalogRepository productCatalogRepository;
    @Autowired
    private final ProductRepository productRepository;


    public MessageController(MessageRepository messageRepository, OrderRepository orderRepository,
                             ProductCatalogRepository productCatalogRepository,
                             ProductRepository productRepository
    ) {
        this.messageRepository = messageRepository;
        this.orderRepository = orderRepository;
        this.productCatalogRepository = productCatalogRepository;
        this.productRepository = productRepository;

    }

    public void createProductCatalogAndProductsAndOrder() {

        // build product catalog, two products, and order

        ProductCatalog productCatalog = new ProductCatalog();

        // right productCatalog: without id; left productCatalog: with id
        // (needed because of autoincrement)
        productCatalog = productCatalogRepository.save(productCatalog);

        Product prod1 = new Product("schroefje", 2);
        prod1 = productRepository.save(prod1);
        Product prod2 = new Product("moertje", 1);
        prod2 = productRepository.save(prod2);

        Order order = new Order();
        order = orderRepository.save(order);

        // build add two products
        productCatalog.add(prod1);
        productCatalog.add(prod2);
        productCatalogRepository.save(productCatalog);

        // "find" a product in the catalog and add it to the order
        Product prod = productCatalog.find(1L);

        // make a copy of the product (the copy has no id yet)
// why a copy is made?
        Product prodCopy = new Product(prod);
        prodCopy = productRepository.save(prodCopy);

        order.add(prodCopy);
        orderRepository.save(order);
    }

    @GetMapping
    public ModelAndView list() {

        createProductCatalogAndProductsAndOrder();

        Iterable<Message> messages = messageRepository.findAll();
        return new ModelAndView("messages/list", "messages", messages);
    }


//    @GetMapping
//    public ModelAndView list() {
//        Iterable<Message> messages = this.messageRepository.findAll();
//        return new ModelAndView("messages/list", "messages", messages);
//    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Message message) {
        return new ModelAndView("messages/view", "message", message);
    }

    @GetMapping(params = "form")
    public String createForm(@ModelAttribute Message message) {
        return "messages/form";
    }

    @PostMapping
    public ModelAndView create(@Valid Message message, BindingResult result,
                               RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("messages/form", "formErrors", result.getAllErrors());
        }
        message = this.messageRepository.save(message);
        redirect.addFlashAttribute("globalMessage", "Successfully created a new message");
        return new ModelAndView("redirect:/{message.id}", "message.id", message.getId());
    }

    @RequestMapping("foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.messageRepository.delete(id);
        Iterable<Message> messages = this.messageRepository.findAll();
        return new ModelAndView("messages/list", "messages", messages);
    }

    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Message message) {
        return new ModelAndView("messages/form", "message", message);
    }

}
