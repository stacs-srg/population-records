/*
 * Copyright 2017 Systems Research Group, University of St Andrews:
 * <https://github.com/stacs-srg>
 *
 * This file is part of the module linkage-java.
 *
 * linkage-java is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * linkage-java is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with linkage-java. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package uk.ac.standrews.cs.population_records;

import uk.ac.standrews.cs.population_records.record_types.Birth;
import uk.ac.standrews.cs.population_records.record_types.Death;
import uk.ac.standrews.cs.population_records.record_types.Marriage;
import uk.ac.standrews.cs.storr.impl.BucketKind;
import uk.ac.standrews.cs.storr.impl.LXP;
import uk.ac.standrews.cs.storr.impl.Store;
import uk.ac.standrews.cs.storr.impl.exceptions.BucketException;
import uk.ac.standrews.cs.storr.impl.exceptions.RepositoryException;
import uk.ac.standrews.cs.storr.interfaces.IBucket;
import uk.ac.standrews.cs.storr.interfaces.IRepository;
import uk.ac.standrews.cs.storr.interfaces.IStore;
import uk.ac.standrews.cs.utilities.dataset.DataSet;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class RecordRepository {

    public static final String BIRTHS_BUCKET_NAME = "birth_records";
    public static final String DEATHS_BUCKET_NAME = "death_records";
    public static final String MARRIAGES_BUCKET_NAME = "marriage_records";

    private IStore store;

    private IBucket<Birth> births;
    private IBucket<Marriage> marriages;
    private IBucket<Death> deaths;

    private String repository_name;

    public RecordRepository(Path store_path, String repository_name) {

        store = new Store(store_path);
        this.repository_name = repository_name;
        try {
            initialiseBuckets(repository_name);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

    }

    public Iterable<Birth> getBirths() {
        return getRecords(births);
    }

    public Iterable<Death> getDeaths() {
        return getRecords(deaths);
    }

    public Iterable<Marriage> getMarriages() {
        return getRecords(marriages);
    }

    public void addBirth(Birth birth) throws BucketException {
        births.makePersistent(birth);
    }

    public void addDeath(Death death) throws BucketException {
        deaths.makePersistent(death);
    }

    public void addMarriage(Marriage marriage) throws BucketException {
        marriages.makePersistent(marriage);
    }

    public void importBirthRecords(DataSet birth_records) throws BucketException {

        for (Birth birth : Birth.convertToRecords(birth_records)) {
            addBirth(birth);
        }
    }

    public void importDeathRecords(DataSet death_records) throws BucketException {

        for (Death death : Death.convertToRecords(death_records)) {
            addDeath(death);
        }
    }

    public void importMarriageRecords(DataSet marriage_records) throws BucketException {

        for (Marriage marriage : Marriage.convertToRecords(marriage_records)) {
            addMarriage(marriage);
        }
    }

    private <T extends LXP> Iterable<T> getRecords(IBucket<T> bucket) {

        return () -> new Iterator<T>() {

            final List<Long> object_ids = bucket.getOids();
            final int bucket_size = object_ids.size();
            int next_index = 0;

            @Override
            public boolean hasNext() {
                return next_index < bucket_size;
            }

            @Override
            public T next() {
                try {
                    return bucket.getObjectById(object_ids.get(next_index++));

                } catch (BucketException e) {
                    return null;
                }
            }
        };
    }

    public void initialiseBuckets(String repository_name) throws RepositoryException {

        IRepository input_repository;
        try {
            input_repository = store.getRepository(repository_name);
        } catch (RepositoryException e) {
            // The repository hasn't previously been initialised.
            input_repository = store.makeRepository(repository_name);
        }

        try{
            births = input_repository.getBucket(BIRTHS_BUCKET_NAME, Birth.class);
        } catch (RepositoryException e) {
            // The bucket hasn't previously been initialised.
            births = input_repository.makeBucket(BIRTHS_BUCKET_NAME, BucketKind.DIRECTORYBACKED, Birth.class);
        }

        try{
            deaths = input_repository.getBucket(DEATHS_BUCKET_NAME, Death.class);
        } catch (RepositoryException e) {
            // The bucket hasn't previously been initialised.
            deaths = input_repository.makeBucket(DEATHS_BUCKET_NAME, BucketKind.DIRECTORYBACKED, Death.class);
        }

        try{
            marriages = input_repository.getBucket(MARRIAGES_BUCKET_NAME, Marriage.class);
        } catch (RepositoryException e) {
            // The bucket hasn't previously been initialised.
            marriages = input_repository.makeBucket(MARRIAGES_BUCKET_NAME, BucketKind.DIRECTORYBACKED, Marriage.class);
        }
    }

    public void setBirthsCacheSize(int size) {
//        births.setCacheSize((int) (births.size() * 1.1));
        try {
            births.setCacheSize(size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setDeathsCacheSize(int size) {
        try {
            deaths.setCacheSize(size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setMarriagesCacheSize(int size) {
        try {
            marriages.setCacheSize(size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumberOfBirths() throws BucketException {
        return births.size();
    }

    public int getNumberOfDeaths() throws BucketException {
        return deaths.size();
    }

    public int getNumberOfMarriages() throws BucketException {
        return marriages.size();
    }

    public void deleteBirthsBucket() throws RepositoryException {
        store.getRepository(repository_name).deleteBucket(BIRTHS_BUCKET_NAME);
    }

    public void deleteDeathsBucket() throws RepositoryException {
        store.getRepository(repository_name).deleteBucket(DEATHS_BUCKET_NAME);
    }

    public void deleteMarriagesBucket() throws RepositoryException {
        store.getRepository(repository_name).deleteBucket(MARRIAGES_BUCKET_NAME);
    }

    public void deleteBucket(String bucketName) throws RepositoryException {
        switch (bucketName) {
            case BIRTHS_BUCKET_NAME:
                deleteBirthsBucket();
                break;
            case MARRIAGES_BUCKET_NAME:
                deleteMarriagesBucket();
                break;
            case DEATHS_BUCKET_NAME:
                deleteDeathsBucket();
                break;
        }
        throw new RuntimeException("Bucket not found: " + bucketName);
    }

    public void stopStoreWatcher() {
        store.getWatcher().stopService();
    }

    public static String[] getBucketNames() {
        return new String[]{BIRTHS_BUCKET_NAME, DEATHS_BUCKET_NAME, MARRIAGES_BUCKET_NAME};
    }

    public IBucket getBucket(String bucketName) {
        switch (bucketName) {
            case BIRTHS_BUCKET_NAME:
                return births;
            case MARRIAGES_BUCKET_NAME:
                return marriages;
            case DEATHS_BUCKET_NAME:
                return deaths;
        }
        throw new RuntimeException("Bucket not found: " + bucketName);
    }

}
