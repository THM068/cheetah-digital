package cheetah.digital.repository

interface BloomFilterRepository <T> {

    boolean exists(T t)

    boolean addObject(T t)

    int count()
}