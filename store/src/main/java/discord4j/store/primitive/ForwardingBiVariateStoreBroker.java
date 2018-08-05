/*
 *  This file is part of Discord4J.
 *
 * Discord4J is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Discord4J.  If not, see <http://www.gnu.org/licenses/>.
 */

package discord4j.store.primitive;

import discord4j.store.Store;
import discord4j.store.broker.BiVariateStoreBroker;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;

@SuppressWarnings("unchecked")
public class ForwardingBiVariateStoreBroker<O extends Serializable, V extends LongObjStore<O>>
        implements BiVariateStoreBroker<V, V> {

    private final BiVariateStoreBroker<Store<Long, O>, Store<Long, O>> broker;

    public ForwardingBiVariateStoreBroker(BiVariateStoreBroker<Store<Long, O>, Store<Long, O>> broker) {
        this.broker = broker;
    }

    @Override
    public Mono<Void> ensureStorePresent(long key1, long key2) {
        return broker.ensureStorePresent(key1, key2);
    }

    @Override
    public Mono<V> getPrimaryStore(long key) {
        return broker.getPrimaryStore(key).map(toForward -> (V) new ForwardingStore<>(toForward));
    }

    @Override
    public Mono<V> getSecondaryStore(long key) {
        return broker.getSecondaryStore(key).map(toForward -> (V) new ForwardingStore<>(toForward));
    }

    @Override
    public Flux<V> getAllPrimaryStores() {
        return broker.getAllPrimaryStores().map(toForward -> (V) new ForwardingStore<>(toForward));
    }

    @Override
    public Flux<V> getAllSecondaryStores() {
        return broker.getAllSecondaryStores().map(toForward -> (V) new ForwardingStore<>(toForward));
    }

    @Override
    public Mono<Void> clearPrimaryStore(long key) {
        return broker.clearPrimaryStore(key);
    }

    @Override
    public Mono<Void> clearSecondaryStore(long key) {
        return broker.clearSecondaryStore(key);
    }

    @Override
    public Mono<Void> clearAllStores() {
        return broker.clearAllStores();
    }
}