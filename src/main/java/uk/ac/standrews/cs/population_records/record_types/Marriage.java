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
package uk.ac.standrews.cs.population_records.record_types;


import uk.ac.standrews.cs.neoStorr.impl.LXPMetadata;
import uk.ac.standrews.cs.neoStorr.impl.StaticLXP;
import uk.ac.standrews.cs.neoStorr.impl.exceptions.IllegalKeyException;
import uk.ac.standrews.cs.neoStorr.impl.exceptions.PersistentObjectException;
import uk.ac.standrews.cs.neoStorr.interfaces.IBucket;
import uk.ac.standrews.cs.neoStorr.types.LXPBaseType;
import uk.ac.standrews.cs.neoStorr.types.LXP_SCALAR;
import uk.ac.standrews.cs.utilities.archive.ErrorHandling;
import uk.ac.standrews.cs.utilities.dataset.DataSet;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Marriage extends StaticLXP {

    public static final String ROLE_BRIDE = "ROLE_BRIDE";
    public static final String ROLE_GROOM = "ROLE_GROOM";
    public static final String ROLE_BRIDES_MOTHER = "ROLE_BRIDES_MOTHER";
    public static final String ROLE_BRIDES_FATHER = "ROLE_BRIDES_FATHER";
    public static final String ROLE_GROOMS_MOTHER = "ROLE_GROOMS_MOTHER";
    public static final String ROLE_GROOMS_FATHER = "ROLE_GROOMS_FATHER";
    public static final String ROLE_PARENTS = "ROLE_PARENTS";  // When both parents are matched

    private static LXPMetadata static_metadata;
    static {

        try {
            static_metadata = new LXPMetadata(Marriage.class, "Marriage");

        } catch (Exception e) {
            ErrorHandling.exceptionError(e);
        }
    }
    public static LXPMetadata getStaticMetaData() {
        return static_metadata;
    }

    public Marriage() {

        super();
    }

    public Marriage(long persistent_object_id, Map properties, IBucket bucket) throws PersistentObjectException, IllegalKeyException {

        super(persistent_object_id, properties, bucket);
    }

    public Marriage(DataSet data, List<String> record) {

        this();
        Utilities.addFieldsToLXP(this, getLabels(), record, data.getColumnLabels());
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof Marriage && ((((Marriage) o).getId()) == getId());
    }

    @Override
    public int hashCode() {
        return new Long(getId()).hashCode();
    }

    @Override
    public LXPMetadata getMetaData() {
        return static_metadata;
    }

    public static List<String> getLabels() {

        return static_metadata.getFieldNamesInSlotOrder();
    }

    public static Iterable<Marriage> convertToRecords(DataSet data_set) {

        return () -> new Iterator<Marriage>() {

            int row = 0;

            final List<List<String>> records = data_set.getRecords();
            final int number_of_rows = records.size();

            @Override
            public boolean hasNext() {
                return row < number_of_rows;
            }

            @Override
            public Marriage next() {
                return new Marriage(data_set, records.get(row++));
            }
        };
    }

    public static DataSet convertToDataSet(Iterable<Marriage> records) {

        return Utilities.toDataSet(records);
    }

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int ORIGINAL_ID;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int STANDARDISED_ID;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_FORENAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_SURNAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_FORENAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_SURNAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int MARRIAGE_DAY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int MARRIAGE_MONTH;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int MARRIAGE_YEAR;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int PLACE_OF_MARRIAGE;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int YEAR_OF_REGISTRATION;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_MOTHER_FORENAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_MOTHER_MAIDEN_SURNAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_FATHER_FORENAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_MOTHER_FORENAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_MOTHER_MAIDEN_SURNAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_FATHER_FORENAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_MARITAL_STATUS;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_MARITAL_STATUS;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_FATHER_SURNAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_FATHER_SURNAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_ADDRESS;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_OCCUPATION;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_ADDRESS;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_OCCUPATION;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_MOTHER_DECEASED;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_FATHER_DECEASED;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_FATHER_DECEASED;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_MOTHER_DECEASED;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_FATHER_OCCUPATION;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_FATHER_OCCUPATION;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int CHANGED_GROOM_FORENAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int IMAGE_QUALITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int ENTRY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_DID_NOT_SIGN;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int DENOMINATION;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int CHANGED_GROOM_SURNAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_DID_NOT_SIGN;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int REGISTRATION_DISTRICT_NUMBER;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int REGISTRATION_DISTRICT_SUFFIX;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_AGE_OR_DATE_OF_BIRTH;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int CHANGED_BRIDE_SURNAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int CORRECTED_ENTRY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int CHANGED_BRIDE_FORENAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_AGE_OR_DATE_OF_BIRTH;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_MOTHER_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_FATHER_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_MOTHER_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_FATHER_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_BIRTH_RECORD_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_BIRTH_RECORD_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_FATHER_BIRTH_RECORD_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_MOTHER_BIRTH_RECORD_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_FATHER_BIRTH_RECORD_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_MOTHER_BIRTH_RECORD_IDENTITY;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_IMMIGRATION_GENERATION;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_IMMIGRATION_GENERATION;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int GROOM_FULL_NAME;

    @LXP_SCALAR(type = LXPBaseType.STRING)
    public static int BRIDE_FULL_NAME;

}
