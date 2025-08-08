package com.chefmooon.differentdoors.common.data;

import com.chefmooon.differentdoors.common.data.types.DoorMaterialType;
import com.chefmooon.differentdoors.common.data.types.DoorStyleType;
import org.jetbrains.annotations.Nullable;

public record DoorInfoRecord(DoorMaterialType doorMaterialType, @Nullable DoorStyleType doorStyleType) {
}
