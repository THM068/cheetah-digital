package cheetah.digital.model

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@Immutable(copyWith = true)
@CompileStatic
class Customer implements Serializable {
    String id
    String name
    String email
}
