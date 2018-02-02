/*
 * This file is part of Discord4J.
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
package discord4j.core.object.entity;

import discord4j.common.json.response.ChannelResponse;
import discord4j.core.Client;
import discord4j.core.object.Snowflake;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

/** A Discord text channel. */
public final class TextChannel extends BaseChannel implements GuildChannel, MessageChannel {

	/** Delegates {@link GuildChannel} operations. */
	private final BaseGuildChannel guildChannel;

	/** Delegates {@link MessageChannel} operations. */
	private final BaseMessageChannel messageChannel;

	/**
	 * Constructs an {@code TextChannel} with an associated client and Discord data.
	 *
	 * @param client The Client associated to this object, must be non-null.
	 * @param channel The raw data as represented by Discord, must be non-null.
	 */
	public TextChannel(final Client client, final ChannelResponse channel) {
		super(client, channel);
		guildChannel = new BaseGuildChannel(client, channel);
		messageChannel = new BaseMessageChannel(client, channel);
	}

	@Override
	public Snowflake getGuildId() {
		return guildChannel.getGuildId();
	}

	@Override
	public Mono<Guild> getGuild() {
		return guildChannel.getGuild();
	}

	@Override
	public Set<PermissionOverwrite> getPermissionOverwrites() {
		return guildChannel.getPermissionOverwrites();
	}

	@Override
	public String getName() {
		return guildChannel.getName();
	}

	@Override
	public Optional<Snowflake> getCategoryId() {
		return guildChannel.getCategoryId();
	}

	@Override
	public Mono<Category> getCategory() {
		return guildChannel.getCategory();
	}

	@Override
	public Mono<Integer> getPosition() {
		return guildChannel.getPosition();
	}

	@Override
	public Optional<Snowflake> getLastMessageId() {
		return messageChannel.getLastMessageId();
	}

	@Override
	public Mono<Message> getLastMessage() {
		return messageChannel.getLastMessage();
	}

	@Override
	public Optional<Instant> getLastPinTimestamp() {
		return messageChannel.getLastPinTimestamp();
	}

	/**
	 * Gets the channel topic.
	 *
	 * @return The channel topic.
	 */
	public String getTopic() {
		return Optional.ofNullable(getChannel().getTopic()).orElse("");
	}

	/**
	 * Gets whether this channel is considered NSFW (Not Safe For Work).
	 *
	 * @return {@code true} if this channel is considered NSFW (Not Safe For Work), {@code false} otherwise.
	 */
	public boolean isNsfw() {
		return Optional.ofNullable(getChannel().getNsfw()).orElseThrow(IllegalStateException::new);
	}

	/**
	 * Gets the <i>raw</i> mention. This is the format utilized to directly mention another text channel (assuming the
	 * text channel exists in context of the mention).
	 *
	 * @return The <i>raw</i> mention.
	 */
	public String getMention() {
		return "<#" + getId().asString() + ">";
	}
}
