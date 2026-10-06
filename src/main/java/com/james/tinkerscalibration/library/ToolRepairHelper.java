package com.james.tinkerscalibration.library;

import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

/**
 * 自动修复工具时的跨模组兼容处理。
 * <p>
 * More Resilient Tinkers（more_resilient_tinkers）会向 {@link ToolDamageUtil#repair} 的头部注入逻辑：
 * 修复武器或盔甲时按被修复的损伤量累加"永久耐久损失"（最高耐久的 3~8% 加上 10~25 点固定值，
 * 记在持久化数据 more_resilient_tinkers:max_durability_lost 上，并在 {@code ToolStack.rebuildStats}
 * 的末尾扣减最高耐久）。该机制是为工作台修复设计的，但本模组的自动修复特性（修复苔藓、雨后春笋、
 * 冰冷、月之力量等）每几 tick 就触发一次，会让工具的最高耐久被持续消耗。
 * <p>
 * 因此这些自动修复统一改走本类：先记下修复前的累计值，修复后若被 More Resilient Tinkers 增加则还原
 * 并重建属性，只抵消本次自动修复产生的损耗；工作台修复与使用磨损造成的损失保持原样。
 * 未安装 More Resilient Tinkers 时该键恒为 0，本方法与直接调用 {@link ToolDamageUtil#repair} 完全等价。
 */
public final class ToolRepairHelper {

    /** More Resilient Tinkers 累计"修复造成的永久耐久损失"所用的持久化键 */
    private static final ResourceLocation MAX_DURABILITY_LOST =
            ResourceLocation.fromNamespaceAndPath("more_resilient_tinkers", "max_durability_lost");

    private ToolRepairHelper() {
    }

    /**
     * 自动修复工具，且不产生 More Resilient Tinkers 的"修复磨损"。
     *
     * @param tool   要修复的工具
     * @param amount 修复量
     */
    public static void repair(IToolStackView tool, int amount) {
        int wearBeforeRepair = tool.getPersistentData().getInt(MAX_DURABILITY_LOST);

        ToolDamageUtil.repair(tool, amount);

        if (tool instanceof ToolStack mutableTool) {
            var data = mutableTool.getPersistentData();
            if (data.getInt(MAX_DURABILITY_LOST) != wearBeforeRepair) {
                data.putInt(MAX_DURABILITY_LOST, wearBeforeRepair);
                mutableTool.rebuildStats();
            }
        }
    }
}
