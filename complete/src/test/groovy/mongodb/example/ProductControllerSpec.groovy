package mongodb.example

import com.mongodb.MongoClient
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory
import grails.plugin.json.view.mvc.JsonViewResolver
import grails.test.mongodb.MongoSpec
import grails.testing.web.controllers.ControllerUnitTest

//tag::spec[]
@SuppressWarnings('MethodName')
class ProductControllerSpec extends MongoSpec implements ControllerUnitTest<ProductController> {
//end::spec[]

    //tag::config[]
    static doWithSpring = {
        jsonSmartViewResolver(JsonViewResolver)
    }

    @Override
    MongoClient createMongoClient() {
        MongodForTestsFactory.with(Version.Main.PRODUCTION).newMongo()
    }
    //end::config[]

    //tag::setup[]
    void setup() {
        Product.saveAll(
            new Product(name: 'Apple', price: 2.0),
            new Product(name: 'Orange', price: 3.0),
            new Product(name: 'Banana', price: 1.0),
            new Product(name: 'Cake', price: 4.0)
        )
    }
    //end::setup[]

    //tag::test[]
    void 'test the search action finds results'() {
        when: 'A query is executed that finds results'
        controller.search('pp', 10)

        then: 'The response is correct'
        response.json.size() == 1
        response.json[0].name == 'Apple'
    }
    //tag::test[]
}
