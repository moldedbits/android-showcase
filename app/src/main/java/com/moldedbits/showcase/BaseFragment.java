package com.moldedbits.showcase;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.moldedbits.showcase.dialogs.LoadingDialog;
import com.moldedbits.showcase.utils.fragmenttransactionhandler.FragmentTransactionHandler;

public abstract class BaseFragment extends Fragment
        implements android.support.v4.app.LoaderManager.LoaderCallbacks<Object> {

    protected FragmentTransactionHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new FragmentTransactionHandler();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //enabling toolbar to have menu items in fragment
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.setActivity(getActivity());
        handler.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.pause();
    }

    public void onDestroy() {
        super.onDestroy();
        handler.setActivity(null);
    }

    public FragmentTransactionHandler getHandler() {
        return handler;
    }

    private LoadingDialog loadingDialog;

    protected void cancelLoadingDialog() {
        if (loadingDialog != null
                && loadingDialog.getDialog() != null
                && loadingDialog.getDialog().isShowing()) {
            loadingDialog.getDialog().dismiss();
        }
    }

    protected void showLoadingDialog(int stringResId) {
        loadingDialog = LoadingDialog.newInstance(getString(stringResId),
                getString(R.string.please_wait), true);
        loadingDialog.show(getActivity().getSupportFragmentManager(), null);
        loadingDialog.setCancelListener(view -> cancelLoadingDialog());
    }
}
