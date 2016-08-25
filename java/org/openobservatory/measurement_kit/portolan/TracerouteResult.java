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
package java.org.openobservatory.measurement_kit.portolan;

import android.util.Patterns;

public class TracerouteResult {
    public final static int DEST_REACHED            = 0;
    public final static int TTL_EXC                 = 1;
    public final static int TIMEOUT_OCCURRED        = 2;
    public final static int NET_ERROR               = 3;
    public final static int ILLEGAL_ARGUMENT        = 4;
    public final static int PROT_UNREACHABLE        = 5;
    public final static int NET_UNREACHABLE         = 6;
    public final static int HOST_UNREACHABLE        = 7;
    public final static int HOST_UNREACHABLE_V6     = 8;
    public final static int ADMIN_UNREACHABLE_V6    = 9;
    public final static int DEST_REACHED_V6         = 10;
    public final static int TTL_EXC_V6              = 11;
    public final static int NOROUTE_HOST_V6         = 12;

    protected String interfaceIp;
    protected int ttl;
    protected double rtt;
    protected int code;
    public int bytes;

    /**
     * Public constructor.
     */
    public ProbeResult(String interfaceIp, int ttl, double rtt, int code,
                       int ipv6, int bytes) {
        setInterfaceIp(interfaceIp,ipv6);
        setTtl(ttl);
        setRtt(rtt);
        setCode(code);
        this.bytes = bytes;
    }

    /**
     * @return ip source address of the probe response
     */
    public String getInterfaceIp() {
        return interfaceIp;
    }

    /**
     * @param interfaceIp source address to be set
     * @throws IllegalArgumentException if @p interfaceIp port is neither
     *                                  a valid IP address nor "*"
     */
    public void setInterfaceIp(String interfaceIp,int ipv6) {
        if (interfaceIp == null) {
            this.interfaceIp = "*";
        } else if (ipv6 == 0) {
            if (Patterns.IP_ADDRESS.matcher(interfaceIp).matches()) {
                this.interfaceIp = interfaceIp;
            }
        } else {
            this.interfaceIp = interfaceIp;
        }
    }

    /**
     * @return Time To Live field of the probe response
     */
    public int getTtl() {
        return ttl;
    }

    /**
     * @param ttl ttl to be set
     * @throws IllegalArgumentException if (ttl < 1 || ttl > 255)
     */
    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    /**
     * @return Round Trip Time (in microseconds) of the probe
     */
    public double getRtt() {
        return rtt;
    }

    /**
     * @param rtt rtt (in microseconds) to be set
     * @throws IllegalArgumentException if (rtt < 0)
     */
    public void setRtt(double rtt) {
        this.rtt = rtt;
    }

    /**
     * @return probe response code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code code to be set
     */
    public void setCode(int code) {
        this.code = code;
    }
};
