package com.humber.capstone;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.humber.capstone.database.AppDatabase;
import com.humber.capstone.database.EmojiDao;
import com.humber.capstone.model.Emoji;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private EmojiDao mDao;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mDao = mDb.emojiDao();
        Log.d(TAG,"CreateDb");
    }
    @After
    public void close(){
        mDb.close();
        Log.d(TAG,"CloseDb");
    }
    @Test
    public void createAndRetrieveNotes(){
        List<Emoji> elist = listOfEmojies();
        mDao.insertAll(elist);
        int count = mDao.getCount();
        for(int i = 1; i<30;i++) {
            Log.d(TAG, mDao.getEmojiById(i).toString());
        }
        Log.d(TAG,"count=" + count);
        assertEquals(elist.size(), count);
    }

    @Test
    public void compareStrings(){

    }


    protected ArrayList<Emoji> listOfEmojies(){
        ArrayList<Emoji> emojiesList = new ArrayList<Emoji>();

        emojiesList.add(new Emoji("angry_face.png", "faces", "angry_face.png"));

        emojiesList.add(new Emoji("angry_face_with_horns.png", "faces", "angry_face_with_horns.png"));
        emojiesList.add(new Emoji("anguished_face.png", "faces", "anguished_face.png"));
        emojiesList.add(new Emoji("anxious_face_with_sweat.png", "faces", "anxious_face_with_sweat.png"));
        emojiesList.add(new Emoji("astonished_face.png", "faces", "astonished_face.png"));
        emojiesList.add(new Emoji("beaming_face_with_smiling_eyes.png", "faces", "beaming_face_with_smiling_eyes.png"));
        emojiesList.add(new Emoji("confounded_face.png", "faces", "confounded_face.png"));
        emojiesList.add(new Emoji("confused_face.png", "faces", "confused_face.png"));
        emojiesList.add(new Emoji("disappointed_face.png", "faces", "disappointed_face.png"));
        emojiesList.add(new Emoji("dizzy_face.png", "faces", "dizzy_face.png"));
        emojiesList.add(new Emoji("downcast_face_with_sweat.png", "faces", "downcast_face_with_sweat.png"));
        emojiesList.add(new Emoji("drooling_face.png", "faces", "drooling_face.png"));
        emojiesList.add(new Emoji("exploding_head.png", "faces", "exploding_head.png"));
        emojiesList.add(new Emoji("expressionless_face.png", "faces", "expressionless_face.png"));
        emojiesList.add(new Emoji("face_blowing_a_kiss.png", "faces", "face_blowing_a_kiss.png"));
        emojiesList.add(new Emoji("face_savoring_food.png", "faces", "face_savoring_food.png"));
        emojiesList.add(new Emoji("face_screaming_in_fear.png", "faces", "face_screaming_in_fear.png"));
        emojiesList.add(new Emoji("face_vomiting.png", "faces", "face_vomiting.png"));
        emojiesList.add(new Emoji("face_with_hand_over_mouth.png", "faces", "face_with_hand_over_mouth.png"));
        emojiesList.add(new Emoji("face_with_head_bandage.png", "faces", "face_with_head_bandage.png"));
        emojiesList.add(new Emoji("face_with_medical_mask.png", "faces", "face_with_medical_mask.png"));
        emojiesList.add(new Emoji("face_with_monocle.png", "faces", "face_with_monocle.png"));
        emojiesList.add(new Emoji("face_with_open_mouth.png", "faces", "face_with_open_mouth.png"));
        emojiesList.add(new Emoji("face_with_raised_eyebrow.png", "faces", "face_with_raised_eyebrow.png"));
        emojiesList.add(new Emoji("face_with_rolling_eyes.png", "faces", "face_with_rolling_eyes.png"));
        emojiesList.add(new Emoji("face_with_steam_from_nose.png", "faces", "face_with_steam_from_nose.png"));
        emojiesList.add(new Emoji("face_with_symbols_on_mouth.png", "faces", "face_with_symbols_on_mouth.png"));
        emojiesList.add(new Emoji("face_with_tears_of_joy.png", "faces", "face_with_tears_of_joy.png"));
        emojiesList.add(new Emoji("face_with_thermometer.png", "faces", "face_with_thermometer.png"));
        emojiesList.add(new Emoji("face_with_tongue.png", "faces", "face_with_tongue.png"));
        emojiesList.add(new Emoji("face_without_mouth.png", "faces", "face_without_mouth.png"));
        emojiesList.add(new Emoji("fearful_face.png", "faces", "fearful_face.png"));
        emojiesList.add(new Emoji("flushed_face.png", "faces", "flushed_face.png"));
        emojiesList.add(new Emoji("frowning_face.png", "faces", "frowning_face.png"));
        emojiesList.add(new Emoji("frowning_face_with_open_mouth.png", "faces", "frowning_face_with_open_mouth.png"));
        emojiesList.add(new Emoji("full_moon_face.png", "faces", "full_moon_face.png"));
        emojiesList.add(new Emoji("grinning_face_with_big_eyes.png", "faces", "grinning_face_with_big_eyes.png"));
        emojiesList.add(new Emoji("grinning_face_with_smiling_eyes.png", "faces", "grinning_face_with_smiling_eyes.png"));
        emojiesList.add(new Emoji("grinning_face_with_sweat.png", "faces", "grinning_face_with_sweat.png"));
        emojiesList.add(new Emoji("grinning_squinting_face.png", "faces", "grinning_squinting_face.png"));
        emojiesList.add(new Emoji("hot_face.png", "faces", "hot_face.png"));
        emojiesList.add(new Emoji("hugging_face.png", "faces", "hugging_face.png"));
        emojiesList.add(new Emoji("hushed_face.png", "faces", "hushed_face.png"));
        emojiesList.add(new Emoji("kissing_face.png", "faces", "kissing_face.png"));
        emojiesList.add(new Emoji("kissing_face_with_closed_eyes.png", "faces", "kissing_face_with_closed_eyes.png"));
        emojiesList.add(new Emoji("kissing_face_with_smiling_eyes.png", "faces", "kissing_face_with_smiling_eyes.png"));
        emojiesList.add(new Emoji("lying_face.png", "faces", "lying_face.png"));
        return emojiesList;
    }



}
