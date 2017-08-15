package demo

import grails.test.mongodb.MongoSpec
import grails.testing.gorm.DomainUnitTest
import com.github.fakemongo.Fongo
import com.mongodb.MongoClient

@SuppressWarnings(['MethodName', 'DuplicateNumberLiteral', 'TrailingWhitespace'])
class ProductSpec extends MongoSpec // <3>
        implements DomainUnitTest<Product> {  // <4>

    @Override
    MongoClient createMongoClient() {  // <2>
        new Fongo(getClass().name).mongo
    }

    void 'a negative value is not a valid price'() { // <1>
        given:
        domain.price = -2.0d

        expect:
        !domain.validate(['price'])
    }

    void 'a blank name is not save'() { // <1>
        given:
        domain.name = ''

        expect:
        !domain.validate(['name'])
    }

    void 'A valid domain is saved'() {  // <1>
        given:
        domain.name = 'Banana'
        domain.price = 2.15d

        when:
        domain.save()

        then:
        Product.count() == old(Product.count()) + 1
    }
}
