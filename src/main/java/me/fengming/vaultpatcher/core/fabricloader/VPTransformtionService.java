package me.fengming.vaultpatcher.core.fabricloader;

import org.spongepowered.asm.service.ITransformer;
import org.spongepowered.asm.service.ITransformerProvider;

import java.util.Collection;
import java.util.List;

public class VPTransformtionService implements ITransformerProvider {
    @Override
    public Collection<ITransformer> getTransformers() {
        return List.of(new VPClassTransformer());
    }

    @Override
    public Collection<ITransformer> getDelegatedTransformers() {
        return null;
    }

    @Override
    public void addTransformerExclusion(String name) {

    }
}
