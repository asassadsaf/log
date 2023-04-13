package com.fkp.log.log4j2;

import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescription;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescriptionImpl;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.apache.logging.log4j.core.util.Integers;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;

@Plugin(name = "MyRolloverStrategy", category = Core.CATEGORY_NAME, printObject = true)
public class MyRolloverStrategy extends DefaultRolloverStrategy {

    private MyRolloverStrategy(DefaultRolloverStrategy defaultRolloverStrategy){
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
            return new MyRolloverStrategy(defaultRolloverStrategy);
        }
    }

    @Override
    public RolloverDescription rollover(RollingFileManager manager) throws SecurityException {
        RolloverDescriptionImpl rollover = (RolloverDescriptionImpl) super.rollover(manager);
        Action asynchronous = rollover.getAsynchronous();
        Action synchronous = rollover.getSynchronous();
        List<Action> list = new ArrayList<>();
        list.add(asynchronous);
        list.add(synchronous);
        Action merge = merge(null, list, false);
        return new RolloverDescriptionImpl(rollover.getActiveFileName(), false, merge, null);
    }

}
