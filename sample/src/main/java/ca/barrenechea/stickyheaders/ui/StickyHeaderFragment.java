/*
 * Copyright 2014 Eduardo Barrenechea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.barrenechea.stickyheaders.ui;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ca.barrenechea.stickyheaders.R;
import ca.barrenechea.stickyheaders.widget.StickyTestAdapter;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;

public class StickyHeaderFragment extends BaseDecorationFragment implements RecyclerView.OnItemTouchListener {
    private StickyHeaderDecoration decor;

    @Override
    protected void setAdapterAndDecor(@NonNull RecyclerView list) {
        final StickyTestAdapter adapter = new StickyTestAdapter(requireContext());
        decor = new StickyHeaderDecoration(adapter);
        setHasOptionsMenu(true);

        list.setAdapter(adapter);
        list.addItemDecoration(decor, 1);
        list.addOnItemTouchListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear_cache) {
            decor.clearHeaderCache();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView,
            @NonNull MotionEvent event) {
        // really bad click detection just for demonstration purposes
        // it will not allow the list to scroll if the swipe motion starts
        // on top of a header
        View v = recyclerView.findChildViewUnder(event.getX(), event.getY());
        return v == null;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {
        // only use the "UP" motion event, discard all others
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return;
        }

        // find the header that was clicked
        View view = decor.findHeaderViewUnder(event.getX(), event.getY());

        if (view instanceof TextView) {
            Toast.makeText(requireContext(), ((TextView) view).getText() + " clicked",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // do nothing
    }
}
