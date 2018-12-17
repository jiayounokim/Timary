package com.github.ojh102.timary.ui.write.store

import android.app.DatePickerDialog
import android.os.Bundle
import com.github.ojh102.timary.R
import com.github.ojh102.timary.base.BaseActivity
import com.github.ojh102.timary.databinding.ActivityStoreBinding
import com.github.ojh102.timary.ui.complete.CompleteType
import com.github.ojh102.timary.util.Navigator
import com.github.ojh102.timary.util.TimaryParser
import com.github.ojh102.timary.util.extension.toast
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_store.*
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StoreActivity : BaseActivity<ActivityStoreBinding, StoreContract.StoreViewModel>() {

    override fun getLayoutRes() = R.layout.activity_store
    override fun getModelClass() = StoreContract.StoreViewModel::class.java

    @Inject
    lateinit var storeAdapter: StoreAdapter

    @Inject
    lateinit var timaryParser: TimaryParser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.inputs = viewModel
        binding.timaryParser = timaryParser

        storeAdapter.callbacks = object : StoreAdapter.Callbacks {
            override fun onItemSelect(item: StoreItem, position: Int) {
                viewModel.inputs.onClickStoreItem(item, position)
            }
        }

        setTimaryToolbar(binding.toolbar)
        initializeRecyclerView()
        bindObservable()
    }

    private fun initializeRecyclerView() {
        rv_store.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rv_store.adapter = storeAdapter
    }

    private fun bindObservable() {
        bind(
                viewModel.outputs.storeDate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeBy {
                            storeAdapter.submitList(it)
                        },

                viewModel.outputs.clickStoreItem()
                        .observeOn(schedulerProvider.ui())
                        .subscribe {
                            if (it.second == 0) {
                                timaryLogger.btnCalendar()
                                showDatePickerDialog()
                            } else {
                                when(it.second) {
                                    1 -> timaryLogger.btnNextSeason()
                                    2 -> timaryLogger.btnLastDay()
                                    3 -> timaryLogger.btnFirstDay()
                                    4 -> timaryLogger.btnRandom()
                                }
                                binding.storeItem = it.first
                            }
                        },

                viewModel.outputs.completeStoreCapsule()
                        .observeOn(schedulerProvider.ui())
                        .doOnNext {
                            timaryLogger.btnComplete(it.content)
                        }
                        .subscribeBy(onNext = {
                            Navigator.navigateToCompleteActivity(
                                    context = this,
                                    type = CompleteType.WRITE,
                                    title = timaryParser.completeWriteText(it.targetDate),
                                    isClear = true
                            )
                        }, onError = {
                            Timber.e(it)
                            toast(it.message)
                        })
        )
    }

    private fun showDatePickerDialog() {
        val cal = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }

        val dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->

            val selectedCal = Calendar.getInstance().apply {
                set(year, month, day)
            }

            binding.storeItem = StoreItem(getString(R.string.store_calendar_selected), selectedCal.timeInMillis)

        }, cal[Calendar.YEAR], cal[Calendar.MONTH], cal[Calendar.DAY_OF_MONTH]).apply {
            setCancelable(false)
            setButton(DatePickerDialog.BUTTON_NEGATIVE, null, { _, _ -> })
            datePicker.minDate = cal.timeInMillis
        }

        dialog.show()
    }

}