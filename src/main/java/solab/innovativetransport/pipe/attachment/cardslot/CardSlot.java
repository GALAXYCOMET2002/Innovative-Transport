package solab.innovativetransport.pipe.attachment.cardslot;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import solab.innovativetransport.card.cardbase.CardBase;
import solab.innovativetransport.card.cardbase.ICardTickable;
import solab.innovativetransport.pipe.attachment.IPipeAttachment;
import solab.innovativetransport.pipe.normal.Pipe;
import solab.innovativetransport.utils.Constants.EnumCards;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND;

public class CardSlot implements IPipeAttachment {

    private List<CardBase> cards;
    private Pipe pipe;

    public CardSlot(Pipe pipeIn) {
        pipe = pipeIn;
        cards = new ArrayList<>(4);
    }

    public boolean insert(CardBase newCard) {
        boolean success = cards.add(newCard);
        if (success) {
            cards.get(cards.size()-1).onCardInserted(pipe);
        }
        return success;
    }

    public List<CardBase> getCards() {
        return cards;
    }

    public void update() {
        for (CardBase card:
             cards) {
            if (card instanceof ICardTickable) {
                ((ICardTickable) card).update();
            }
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagList tagList = new NBTTagList();
        for (CardBase card:cards) {
            tagList.appendTag(card.writeToNBT(new NBTTagCompound()));
        }
        compound.setTag("IT_CardSlot_Cards",tagList);
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound) {
        cards.clear();
        NBTTagList tagList = compound.getTagList("IT_CardSlot_Cards", TAG_COMPOUND);
        for (int i=0; i<tagList.tagCount(); i++) {
            NBTTagCompound compound1 = tagList.getCompoundTagAt(i);
            CardBase card = CardBase.getCardFromType(EnumCards.values()[compound1.getInteger("IT_Card_Type")],compound1.getInteger("IT_Card_Tier"));
            if (card != null) {
                card.readFromNBT(compound1);
                card.onCardInserted(pipe);
                cards.add(card);
            }
        }
    }
}
