package ua.lelpel.pomobluelw.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import at.grabner.circleprogress.CircleProgressView;
import ua.lelpel.pomobluelw.R;
import ua.lelpel.pomobluelw.model.TaskManager;
import ua.lelpel.pomobluelw.timer.PomodoroTimer;
import ua.lelpel.pomobluelw.timer.TimerEventListener;
import ua.lelpel.pomobluelw.ui.fragments.AddTaskDialog;

public class TimerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView taskName;
    private static TaskManager taskManager = TaskManager.getInstance();

    AddTaskDialog newTaskDialog = new AddTaskDialog();
    //делает прерывания / паузы в перерыве
    private Button interruptButton;
    //инвертирует этап
    private Button nextStageButton;
    //круг
    CircleProgressView circleClock;

    {

    }

    //сам таймер
    private PomodoroTimer timer = new PomodoroTimer();

    //число завершенных этапов-помидор
    private int finishedPomodoros;


    public TaskManager getTaskManager() {
        return taskManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        interruptButton = (Button) findViewById(R.id.pause_button);
        nextStageButton = (Button) findViewById(R.id.next_stage_button);
        circleClock = (CircleProgressView) findViewById(R.id.circleView);
        taskName = (TextView) findViewById(R.id.taskName);

        onViewInitialization();
        setTimerListener();
        //setTaskChangeListener();
        setButtonOCLs();

        //TimerNotification.notify(this, "Кек", 0);
        timer.run();

        interruptButton.setEnabled(true);
        interruptButton.setText("Прерывание");
        //-------------------------------------------------------------------
        FloatingActionButton nextStageButton = (FloatingActionButton) findViewById(R.id.fab);
        nextStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskDialog.show(getFragmentManager(), "newTask");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //гамбургер
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(TimerActivity.this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.nav_boards:
                intent.setClass(TimerActivity.this, BoardSelectorActivity.class);
                break;
            case R.id.nav_my_tasks:
                intent.setClass(TimerActivity.this, TaskSelectorActivity.class);
                break;
            case R.id.nav_stats:
                intent.setClass(TimerActivity.this, StatsActivity.class);
                break;
        }

        startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onViewInitialization() {
        //находим круговой таймер

        //заполняем его полностью
        circleClock.setMaxValue(timer.getCurrentMaxTicks());
        circleClock.setValue(timer.getCurrentMaxTicks());

        //настраиваем отображение
//        circleClock.setTextMode(TextMode.TEXT);
//        circleClock.setUnitVisible(false);

        taskName.setText(taskManager.getCurrentTaskName());

        interruptButton.setEnabled(false);
        nextStageButton.setText("За работу");
    }

    private void setTimerListener() {
        timer.setListener(new TimerEventListener() {
            @Override
            /**
             * Тик таймера -> отображение
             */
            public void onTimerTick(int ticks) {
                circleClock.setValue(ticks);

                int minutes = (ticks %3600)/60;
                int secs = ticks %60;

                String time = String.format(Locale.ENGLISH, "%02d:%02d", minutes, secs);
                circleClock.setText(time);
            }

            @Override
            /**
             * Переключение режимов
             */
            public void onModeChanged(boolean isActive) {
                //timer.pause();
                if (isActive) {
                    //Snackbar.make(circleClock, "Приступить к отдыху", Snackbar.LENGTH_INDEFINITE).setAction("Да", onBreakStartListener).show();
                    Toast.makeText(TimerActivity.this, "Время отдыха", Toast.LENGTH_LONG).show();
                    nextStageButton.setText("На отдых");
                    interruptButton.setText("Прерывание");
                } else {
                    //Snackbar.make(circleClock, "Приступить к работе", Snackbar.LENGTH_INDEFINITE).setAction("Да", onActiveStartListener).show();
                    Toast.makeText(TimerActivity.this, "Время работы", Toast.LENGTH_LONG).show();
                    finishedPomodoros++;
                    if (finishedPomodoros == 3) {
                        finishedPomodoros = 0;
                    }
                    nextStageButton.setText("За работу");
                    interruptButton.setText("Пауза");
                }
                circleClock.setMaxValue(timer.getCurrentMaxTicks());
            }
        });
    }

//    private void setTaskChangeListener() {
//        SpinnerTaskSelectorActivity.setTaskChosenListener(new SpinnerTaskSelectorActivity.TaskChosenListener() {
//            @Override
//            public void taskChosen(Task currentTask) {
//                TimerActivity.this.currentTask = currentTask;
//                taskName.setText(currentTask.toString());
//            }
//        });
//    }

    private void setButtonOCLs() {
        interruptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer.isRunning()) {
                    timer.pause();
                }
            }
        });

        nextStageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timer.wasRunning()) {
                    timer.run();
                } else {
                    timer.invertMode();
                }
            }
        });
    }
    public void onClickStart(View view) {
        timer.startCountdown();
    }

    public void onClickInterrupt(View view) {
        timer.pause();
    }

    public void onClickReset(View view) {
        timer.reset();
        finishedPomodoros = 0;
    }
}
