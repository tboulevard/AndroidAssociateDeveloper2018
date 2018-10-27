package com.boulevard.androidassociatedeveloper2018java.boardingpass;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boulevard.androidassociatedeveloper2018java.R;
import com.boulevard.androidassociatedeveloper2018java.common.models.BoardingPassInfo;
import com.boulevard.androidassociatedeveloper2018java.common.util.FakeDataUtils;
import com.boulevard.androidassociatedeveloper2018java.databinding.ActivityBoardingPassBinding;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BoardingPassActivity extends AppCompatActivity {

    ActivityBoardingPassBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * DataBindUtil.setContentView replaces our normal call of setContent view.
         * DataBindingUtil also created our ActivityBoardingPassBinding that we will eventually use
         * to display all of our data.
         *
         * This sets the context of our binding class to elements in the layout we just passed
         */
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_boarding_pass);

        BoardingPassInfo fakeBoardingInfo = FakeDataUtils.generateFakeBoardingPassInfo();

        displayBoardingPassInfo(fakeBoardingInfo);
    }

    private void displayBoardingPassInfo(BoardingPassInfo info) {

        mBinding.textViewPassengerName.setText(info.passengerName);
        mBinding.boardingInfo.textViewOriginAirport.setText(info.originCode);
        mBinding.boardingInfo.textViewFlightCode.setText(info.flightCode);
        mBinding.boardingInfo.textViewDestinationAirport.setText(info.destCode);

        SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault());
        String boardingTime = formatter.format(info.boardingTime);
        String departureTime = formatter.format(info.departureTime);
        String arrivalTime = formatter.format(info.arrivalTime);

        mBinding.textViewBoardingTime.setText(boardingTime);
        mBinding.textViewDepartureTime.setText(departureTime);
        mBinding.textViewArrivalTime.setText(arrivalTime);

        // Use TimeUnit methods to format the total minutes until boarding
        long totalMinutesUntilBoarding = info.getMinutesUntilBoarding();
        long hoursUntilBoarding = TimeUnit.MINUTES.toHours(totalMinutesUntilBoarding);
        long minutesLessHoursUntilBoarding =
                totalMinutesUntilBoarding - TimeUnit.HOURS.toMinutes(hoursUntilBoarding);

        String hoursAndMinutesUntilBoarding = getString(R.string.countDownFormat,
                hoursUntilBoarding,
                minutesLessHoursUntilBoarding);

        mBinding.textViewBoardingInCountdown.setText(hoursAndMinutesUntilBoarding);
        mBinding.boardingTable.textViewTerminal.setText(info.departureTerminal);
        mBinding.boardingTable.textViewGate.setText(info.departureGate);
        mBinding.boardingTable.textViewSeat.setText(info.seatNumber);
    }
}
