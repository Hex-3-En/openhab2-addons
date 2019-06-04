package org.openhab.binding.deconz.internal.api.contract;

import org.eclipse.jdt.annotation.Nullable;

public interface GroupState {
    public @Nullable Boolean any_on();

    public @Nullable Boolean all_on();
}
