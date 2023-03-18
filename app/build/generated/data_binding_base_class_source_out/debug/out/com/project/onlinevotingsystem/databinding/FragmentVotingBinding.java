// Generated by view binder compiler. Do not edit!
package com.project.onlinevotingsystem.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.agrawalsuneet.dotsloader.loaders.LinearDotsLoader;
import com.project.onlinevotingsystem.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentVotingBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final LinearDotsLoader loadingprogress;

  @NonNull
  public final TextView votenametext;

  @NonNull
  public final LinearLayout voteoption;

  @NonNull
  public final LinearLayout voteoption2;

  @NonNull
  public final LinearLayout votesubmitbuttonlayout;

  private FragmentVotingBinding(@NonNull FrameLayout rootView,
      @NonNull LinearDotsLoader loadingprogress, @NonNull TextView votenametext,
      @NonNull LinearLayout voteoption, @NonNull LinearLayout voteoption2,
      @NonNull LinearLayout votesubmitbuttonlayout) {
    this.rootView = rootView;
    this.loadingprogress = loadingprogress;
    this.votenametext = votenametext;
    this.voteoption = voteoption;
    this.voteoption2 = voteoption2;
    this.votesubmitbuttonlayout = votesubmitbuttonlayout;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentVotingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentVotingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_voting, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentVotingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.loadingprogress;
      LinearDotsLoader loadingprogress = ViewBindings.findChildViewById(rootView, id);
      if (loadingprogress == null) {
        break missingId;
      }

      id = R.id.votenametext;
      TextView votenametext = ViewBindings.findChildViewById(rootView, id);
      if (votenametext == null) {
        break missingId;
      }

      id = R.id.voteoption;
      LinearLayout voteoption = ViewBindings.findChildViewById(rootView, id);
      if (voteoption == null) {
        break missingId;
      }

      id = R.id.voteoption2;
      LinearLayout voteoption2 = ViewBindings.findChildViewById(rootView, id);
      if (voteoption2 == null) {
        break missingId;
      }

      id = R.id.votesubmitbuttonlayout;
      LinearLayout votesubmitbuttonlayout = ViewBindings.findChildViewById(rootView, id);
      if (votesubmitbuttonlayout == null) {
        break missingId;
      }

      return new FragmentVotingBinding((FrameLayout) rootView, loadingprogress, votenametext,
          voteoption, voteoption2, votesubmitbuttonlayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
