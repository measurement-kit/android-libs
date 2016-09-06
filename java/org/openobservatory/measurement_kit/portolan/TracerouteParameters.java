/*-
 * Part of measurement-kit <https://measurement-kit.github.io/>.
 * Measurement-kit is free software. See AUTHORS and LICENSE for more
 * information on the copying conditions.
 * =========================================================================
 *
 * Portions Copyright (c) 2015, Adriano Faggiani, Enrico Gregori,
 * Luciano Lenzini, Valerio Luconi
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

// Adapted from: utilspkg/ProbeParameters.java

package org.openobservatory.measurement_kit.portolan;

/**
 * Parameters of the probe.
 */
public class TracerouteParameters {

    public String destIp;
    public int destPort;
    public int ttl;
    public int timeout;

    /**
     * Public constructor.
     */
    public TracerouteParameters(String destIp, int destPort, int ttl,
                                int timeout) {
        setDestIp(destIp);
        setDestPort(destPort);
        setTtl(ttl);
        setTimeout(timeout);
    }

    /**
     * @return probe destination ip
     */
    public String getDestIp() {
        return destIp;
    }

    /**
     * @param destIp destination address to be set
     * @throws IllegalArgumentException if @p destIp port is not
     *                                  a valid IP address
     */
    public void setDestIp(String destIp) throws IllegalArgumentException {
        if (!TracerouteUtils.isIPv4(destIp) && !TracerouteUtils.isIPv6(destIp)) {
            throw new IllegalArgumentException("Destination IP must be in "
                + "the form a.b.c.d or a:b:c:d:e:f:g:h : " + destIp);
        }
        this.destIp = destIp;
    }

    /**
     * @return probe destination port
     */
    public int getDestPort() {
        return destPort;
    }

    /**
     * @param destPort destination port to be set
     * @throws IllegalArgumentException if (port < 0 || port > 65535)
     */
    public void setDestPort(int destPort) throws IllegalArgumentException {
        if (destPort < 0 || destPort > 65535) {
            throw new IllegalArgumentException("Destination port must "
                + "be in the range [0, 65535]");
        }
        this.destPort = destPort;
    }

    /**
     * @return probe Time To Live
     */
    public int getTtl() {
        return ttl;
    }

    /**
     * @param ttl ttl to be set
     * @throws IllegalArgumentException if (ttl < 1 || ttl > 255)
     */
    public void setTtl(int ttl) throws IllegalArgumentException {
        if (ttl < 1 || ttl > 255) {
            throw new IllegalArgumentException("TTL must be in the " +
                "range [1, 255]");
        }
        this.ttl = ttl;
    }

    /**
     * @return probe timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * @param timeout timeout to be set
     * @throws IllegalArgumentException if @p timeout is not greater than 0
     */
    public void setTimeout(int timeout) throws IllegalArgumentException {
        if (timeout <= 0) {
            throw new IllegalArgumentException("Timeout must be positive");
        }
        this.timeout = timeout;
    }
};
