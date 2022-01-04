package pl.ale.util;


public class Util {

    public static int calculateNumberOfPages(int numberOfRepos, int pageSize) {

        return (int) Math.ceil((double) numberOfRepos / pageSize);
    }
}
