package ua.lelpel.pomobluelw.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ua.lelpel.pomobluelw.R;
import ua.lelpel.pomobluelw.model.Task;
import ua.lelpel.pomobluelw.model.TaskManager;
import ua.lelpel.pomobluelw.ui.fragments.EditTaskDialog;

public class TaskSelectorActivity extends AppCompatActivity {
    private static TaskManager taskManager = TaskManager.getInstance();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_selector);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_selector, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a TasksInListFragment (defined as a static inner class below).
            return SwipeFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return taskManager.getBoardCount();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return taskManager.getBoardName(position);
        }
    }


    public static class SwipeFragment extends ListFragment {
        private static final String ARG_LIST_ID = "section_number";
        TaskAdapter taskAdapter;

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu,v, menuInfo);

            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.menu_task_list_context, menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            if (getUserVisibleHint()) {
                switch (item.getItemId()) {
                    case R.id.menu_item_edit_task:
                        EditTaskDialog dialog = EditTaskDialog.newInstance((int)getListView().getSelectedItemId(), getArguments().getInt(ARG_LIST_ID));
                        dialog.show(getActivity().getFragmentManager(), "editTask");
                        taskAdapter.setTasks(taskManager.getTasksInBoard(getArguments().getInt(ARG_LIST_ID)));
                        taskAdapter.notifyDataSetChanged();

                        //taskManager.deleteTask((int)getListView().getSelectedItemId(), getArguments().getInt(ARG_LIST_ID));
                        break;
                    case R.id.menu_item_remove_task:
                        taskManager.deleteTask((int)getListView().getSelectedItemId(), getArguments().getInt(ARG_LIST_ID));
                        taskAdapter.setTasks(taskManager.getTasksInBoard(getArguments().getInt(ARG_LIST_ID)));
                        taskAdapter.notifyDataSetChanged();
                        break;
                }
            }

            return true;
        }

        @Override
        public void onResume() {
            super.onResume();
            getListView().destroyDrawingCache();
            taskAdapter.notifyDataSetChanged();
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            registerForContextMenu(getListView());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final int listId = getArguments().getInt(ARG_LIST_ID);

            taskAdapter = new TaskAdapter(inflater.getContext(), taskManager.getTasksInBoard(listId));

            setListAdapter(taskAdapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }


        public static SwipeFragment newInstance(int sectionNumber) {
            SwipeFragment fragment = new SwipeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_LIST_ID, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            taskManager.setCurrentTask((int)id, getArguments().getInt(ARG_LIST_ID));
            //Log.i("selectedtask", taskManager.getTaskNameByIndexes((int)id, getArguments().getInt(ARG_LIST_ID)));
        }

        private class TaskAdapter extends BaseAdapter {
            Context ctx;
            LayoutInflater lInflater;

            List<Task> tasks;

            public TaskAdapter(Context ctx, List<Task> tasks) {
                this.ctx = ctx;
                lInflater = (LayoutInflater) ctx
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                this.tasks = tasks;
            }

            public void setTasks(List<Task> tasks) {
                this.tasks.clear();
                notifyDataSetChanged();
                this.tasks = tasks;
                notifyDataSetChanged();
            }

            @Override
            public int getCount() {
                return tasks.size();
            }

            @Override
            public Object getItem(int position) {
                return tasks.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    view = lInflater.inflate(R.layout.task_item, parent, false);
                }

                Task task = (Task) getItem(position);

                ((TextView) view.findViewById(R.id.task_item_name)).setText(task.getName());
                ((TextView) view.findViewById(R.id.task_item_deadline)).setText(task.getDeadline());
                ((ImageView) view.findViewById(R.id.priority_circle)).
                        setColorFilter(getColorByPriority(task.getPriority()), PorterDuff.Mode.SRC_IN);

                return view;

            }

            private int getColorByPriority(int priority) {
                switch (priority) {
                    case 0:
                        return Color.rgb(0, 100, 0);
                    case 1:
                        return Color.rgb(255, 165, 0);
                    case 2:
                        return Color.RED;
                    default:
                        return Color.GRAY;
                }
            }
        }
    }
}