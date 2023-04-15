package com.fkp.action;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescription;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescriptionImpl;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.appender.rolling.action.CompositeAction;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;

import java.util.ArrayList;
import java.util.List;

@Plugin(name = "SignRolloverStrategy", category = Core.CATEGORY_NAME, printObject = true)
public class SignRolloverStrategy extends DefaultRolloverStrategy {

    private SignRolloverStrategy(DefaultRolloverStrategy defaultRolloverStrategy){
        super(defaultRolloverStrategy.getMinIndex(), defaultRolloverStrategy.getMaxIndex(), defaultRolloverStrategy.isUseMax(), defaultRolloverStrategy.getCompressionLevel(),
                defaultRolloverStrategy.getStrSubstitutor(), defaultRolloverStrategy.getCustomActions().toArray(new Action[0]), defaultRolloverStrategy.isStopCustomActionsOnError(),
                defaultRolloverStrategy.getTempCompressedFilePattern() == null ? null : defaultRolloverStrategy.getTempCompressedFilePattern().getPattern());
    }
    @PluginBuilderFactory
    public static Builder newBuilder() {
        return new MyBuild();
    }

    private static class MyBuild extends Builder{
        @Override
        public DefaultRolloverStrategy build() {
            DefaultRolloverStrategy defaultRolloverStrategy = super.build();
            return new SignRolloverStrategy(defaultRolloverStrategy);
        }
    }

    @Override
    public RolloverDescription rollover(RollingFileManager manager) throws SecurityException {
        RolloverDescriptionImpl rollover = (RolloverDescriptionImpl) super.rollover(manager);
        CompositeAction asynchronous = (CompositeAction) rollover.getAsynchronous();
        if(asynchronous != null){
            Action[] actions = asynchronous.getActions();
            if(actions != null){
                List<Action> syncActions = new ArrayList<>();
                List<Action> asyncActions = new ArrayList<>();
                for (Action action : actions) {
                    if(action instanceof SignAction){
                        syncActions.add(action);
                    }else {
                        asyncActions.add(action);
                    }
                }
                Action synchronous = rollover.getSynchronous();
                syncActions.add(synchronous);
                return new RolloverDescriptionImpl(rollover.getActiveFileName(), false, merge(null, syncActions, false), merge(null, asyncActions, false));
            }
        }
        return rollover;
    }

}
