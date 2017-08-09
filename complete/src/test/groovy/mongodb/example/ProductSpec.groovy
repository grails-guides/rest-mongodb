package mongodb.example

//tag::imports[]
import com.mongodb.MongoClient
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory
import grails.test.mongodb.MongoSpec
import grails.testing.gorm.DomainUnitTest
//end::imports[]

//tag::spec[]
//tag::mongoSpec[]
@SuppressWarnings(['MethodName', 'DuplicateNumberLiteral'])
class ProductSpec extends MongoSpec implements DomainUnitTest<Product> {
//end::mongoSpec[]

    //tag::createClient[]
    @Override
    MongoClient createMongoClient() {
        MongodForTestsFactory.with(Version.Main.PRODUCTION).newMongo()
    }
    //end::createClient[]

    //tag::testName[]
    void 'test domain class validation'() {
    //end::testName[]
        //tag::testInvalid[]
        when: 'A domain class is saved with invalid data'
        Product product = new Product(name: '', price: -2.0d)
        product.save()

        then: 'There were errors and the data was not saved'
        product.hasErrors()
        product.errors.getFieldError('price')
        product.errors.getFieldError('name')
        Product.count() == 0
        //end::testInvalid[]

        //tag::testValid[]
        when: 'A valid domain is saved'
        product.name = 'Banana'
        product.price = 2.15d
        product.save()

        then: 'The product was saved successfully'
        Product.count() == 1
        Product.first().price == 2.15d
        //end::testValid[]
    }
}
//end::spec[]
