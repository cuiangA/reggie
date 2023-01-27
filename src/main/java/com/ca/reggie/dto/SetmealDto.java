package com.ca.reggie.dto;


import com.ca.reggie.pojo.Setmeal;
import com.ca.reggie.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
