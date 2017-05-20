package com.rviannaoliveira.vroutetrack;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rviannaoliveira.vroutetrack.data.AppDatabase;
import com.rviannaoliveira.vroutetrack.data.RegisterDao;
import com.rviannaoliveira.vroutetrack.model.RegisterTrack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RouteTrackTest {
    private RegisterDao registerDao;
    private AppDatabase appDatabase;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        registerDao = appDatabase.registerDao();
    }

    @After
    public void closeDb() throws IOException {
        appDatabase.close();
    }

    @Test
    public void createRegisterAndRead() throws Exception {
        RegisterTrack register = new RegisterTrack();
        register.setId(1);
        register.setAddress("Rua Almeida de morais, 111");
        registerDao.insert(register);
        assertThat(registerDao.findRegisterById(1).getId(), equalTo(register.getId()));
    }
}
