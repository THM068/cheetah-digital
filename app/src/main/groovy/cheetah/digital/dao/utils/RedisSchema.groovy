package cheetah.digital.dao.utils

class RedisSchema {

    static String getCustomerHashKey(String id) {
        "customer:info:${id}"
    }
}
