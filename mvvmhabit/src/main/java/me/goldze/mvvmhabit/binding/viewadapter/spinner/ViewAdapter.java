package me.goldze.mvvmhabit.binding.viewadapter.spinner;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import me.goldze.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by goldze on 2017/6/18.
 */
public class ViewAdapter {
    /**
     * 双向的SpinnerViewAdapter, 可以监听选中的条目,也可以回显选中的值
     *
     * @param spinner 控件本身
     * @param itemDatas 下拉条目
     * @param valueReply 回显的value
     * @param bindingCommand 条目点击的监听
     */
    @BindingAdapter(value = {"itemDatas", "valueReply", "onItemSelectedCommand"})
    public static void onItemSelectedCommand(final Spinner spinner, final List<IKeyAndValue> itemDatas, String valueReply, final BindingCommand<IKeyAndValue> bindingCommand) {
        if (itemDatas == null) {
            throw new NullPointerException("this itemDatas parameter is null");
        }
        List<String> lists = new ArrayList<>();
        for (IKeyAndValue IKeyAndValue : itemDatas) {
            lists.add(IKeyAndValue.getKey());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(spinner.getContext(), android.R.layout.simple_spinner_item, lists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IKeyAndValue IKeyAndValue = itemDatas.get(position);
                //键值都不为空才回调
//                if (!(IKeyAndValue.getKey() == null || (IKeyAndValue.getValue() == null))) {
//                    bindingCommand.execute(IKeyAndValue);
//                }
                bindingCommand.execute(IKeyAndValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //回显选中的值
        if (!TextUtils.isEmpty(valueReply)) {
            for (int i = 0; i < itemDatas.size(); i++) {
                IKeyAndValue IKeyAndValue = itemDatas.get(i);
                if (IKeyAndValue.getValue().equals(valueReply)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }
    }
}