package com.eduhub.siu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new Author1();
        } else if (position == 1) {
            return new Author2();
        }
        else if (position == 2) {
            return new Author3();
        }
        else if (position == 3) {
            return new Author4();
        }

        else {
            return new Author1(); // Default fragment if position is out of range
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Number of tabs
    }
}
