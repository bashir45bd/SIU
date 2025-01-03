package com.eduhub.siu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter2 extends FragmentStateAdapter {

    public ViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity2) {
        super(fragmentActivity2);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position2) {
        if (position2 == 0) {
            return new Treasurer();
        } else if (position2 == 1) {
            return new Registrar();
        }
        else if (position2 == 2) {
            return new Exam_control();
        }
        else if (position2 == 3) {
            return new Librarian();
        }

        else {
            return new Treasurer(); // Default fragment if position is out of range
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Number of tabs
    }
}
