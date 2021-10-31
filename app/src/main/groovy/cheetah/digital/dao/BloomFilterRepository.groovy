package cheetah.digital.dao

interface BloomFilterRepository <T> {

    boolean exists(T t)

    boolean addObject(T t)

    long count()
}