/*
 *     Copyright (c) 2022, xwjcool.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cool.xwj.blocktuner.mixin;

import cool.xwj.blocktuner.NoteNames;
import net.minecraft.block.NoteBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class NoteNameMixin {

    @Unique
    private static final Style NOTE_STYLE = Style.EMPTY.withColor(Formatting.AQUA);

    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    private void getNoteName(CallbackInfoReturnable<Text> cir){
        ItemStack itemStack = (ItemStack)(Object)this;
        if (itemStack.getItem() == Items.NOTE_BLOCK && itemStack.get(DataComponentTypes.BLOCK_STATE) != null) {
            int note = itemStack.get(DataComponentTypes.BLOCK_STATE).getValue(NoteBlock.NOTE);
            cir.setReturnValue(MutableText.of(new TranslatableTextContent(((ItemStack)(Object)this).getTranslationKey(), null, null))
                    .append(MutableText.of(new PlainTextContent.Literal(" (" + NoteNames.get(note) + ", "+ note + ")")).setStyle(NOTE_STYLE)));
        }
    }
}
