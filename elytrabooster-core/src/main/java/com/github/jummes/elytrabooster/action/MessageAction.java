package com.github.jummes.elytrabooster.action;

import com.github.jummes.elytrabooster.action.target.PlayerTarget;
import com.github.jummes.elytrabooster.action.target.Target;
import com.github.jummes.libs.annotation.Serializable;
import com.github.jummes.libs.util.MessageUtils;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class MessageAction extends AbstractAction {

    private static final String HEAD = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWIyMDY0MzkwZTc5ZDllNTRjY2I0MThiMDczMzE1M2NmOTkyM2ZjNGE4ZDE0YWIxZDJiN2VmNTk2ODgzMWM5MyJ9fX0=";

    @Serializable(headTexture = HEAD)
    private String message;

    public MessageAction(){
        this("");
    }

    public static MessageAction deserialize(Map<String, Object> map) {
        String message = (String) map.get("message");
        return new MessageAction(message);
    }

    @Override
    public void execute(Target target) {
        ((PlayerTarget) target).getTarget().sendMessage(MessageUtils.color(message));
    }

    @Override
    public List<Class<? extends Target>> getPossibleTargets() {
        return Lists.newArrayList(PlayerTarget.class);
    }

}