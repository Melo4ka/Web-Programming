package ru.meldren.lab3.storage;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.meldren.weblab3.entity.Result;
import ru.meldren.weblab3.storage.ResultStorage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Meldren on 19/04/2023
 */
@Timeout(2)
class ResultStorageTest {

    static ResultStorage storage;

    @BeforeAll
    static void initDatabase() {
        storage = new ResultStorage();
    }

    @Test
    void checkIfResultAddsToTheDatabase() {
        Random random = ThreadLocalRandom.current();
        Result result = new Result(
                random.nextInt(),
                random.nextInt(),
                random.nextInt(),
                false,
                random.nextLong()
        );
        storage.addResult(result);
        List<Result> results = storage.getAllResults();
        assertEquals(result, results.get(results.size() - 1));
    }
}
