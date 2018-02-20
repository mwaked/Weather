package com.example.crizmamegastore.weather.Database;

import com.example.crizmamegastore.weather.Model.CitiesModel_;
import io.objectbox.BoxStoreBuilder;
import io.objectbox.ModelBuilder;
import io.objectbox.model.PropertyFlags;
import io.objectbox.model.PropertyType;


public class WeatherObjectBox {

    public static BoxStoreBuilder builder() {
        BoxStoreBuilder builder = new BoxStoreBuilder(getModel());
        builder.entity(CitiesModel_.__INSTANCE);
        return builder;
    }

    private static byte[] getModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.lastEntityId(1, 623186193402325011L);
        modelBuilder.lastIndexId(0, 0L);
        modelBuilder.lastRelationId(0, 0L);

        ModelBuilder.EntityBuilder entityBuilder;

        entityBuilder = modelBuilder.entity("user_cities");
        entityBuilder.id(1, 623186193402325011L).lastPropertyId(15, 6677861563612759597L);
        entityBuilder.flags(io.objectbox.model.EntityFlags.USE_NO_ARG_CONSTRUCTOR);
        entityBuilder.property("id", PropertyType.Long).id(1, 1829565021229914829L)
                .flags(PropertyFlags.ID | PropertyFlags.NOT_NULL);
        entityBuilder.property("city_name", PropertyType.String).id(9, 5154482317757090988L);
        entityBuilder.property("lat", PropertyType.String).id(10, 4701169082191358090L);
        entityBuilder.property("lng", PropertyType.String).id(11, 4953139529009513301L);
        entityBuilder.entityDone();

        return modelBuilder.build();
    }

}
