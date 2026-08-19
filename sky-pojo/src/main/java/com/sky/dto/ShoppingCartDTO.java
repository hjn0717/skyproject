package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

public class ShoppingCartDTO implements Serializable {

    private Long dishId;
    private Long setmealId;
    private String dishFlavor;
	public Long getDishId() {
		return dishId;
	}
	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}
	public Long getSetmealId() {
		return setmealId;
	}
	public void setSetmealId(Long setmealId) {
		this.setmealId = setmealId;
	}
	public String getDishFlavor() {
		return dishFlavor;
	}
	public void setDishFlavor(String dishFlavor) {
		this.dishFlavor = dishFlavor;
	}

}
