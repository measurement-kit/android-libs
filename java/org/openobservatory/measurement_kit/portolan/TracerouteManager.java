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

import java.util.Random;
import org.openobservatory.measurement_kit.jni.sync;

public class TracerouteManager {

    private static TracerouteManager mInstance;
    private int[] mPorts;
    private boolean[] mPortFree;
    private int mTotalProbesCount;
    private int mTotalICMPCount;

    private TracerouteManager() {
        mPorts = new int[64512];
        mPortFree = new boolean[64512];
        mTotalProbesCount = 0;
        mTotalICMPCount = 0;
        for(int i = 0; i < 64512; i++) {
            mPorts[i] = i + 1025;
            mPortFree[i] = true;
        }
    }

    public static TracerouteManager getInstance() {
        if (mInstance == null) {
            synchronized (TracerouteManager.class) {
                if (mInstance == null) {
                    mInstance = new TracerouteManager();
                }
            }
        }
        return mInstance;
    }

    public TracerouteResult trace(TracerouteParameters parameters, long prober) {
        TraceResult res = new TraceResult();
        Traceroute.trace(res, prober, parameters.destIp, parameters.destPort,
                         parameters.ttl, parameters.timeout / 1000.0);

        int code = TracerouteResult.NET_ERROR;
        if (res.isIPv4) {
            if (res.code.equals("GOT_REPLY_PACKET")) {
                code = TracerouteResult.DEST_REACHED;
            } else if (res.code.equals("TTL_EXCEEDED")) {
                code = TracerouteResult.TTL_EXC;
            } else if (res.code.equals("PORT_IS_CLOSED")) {
                code = TracerouteResult.DEST_REACHED;
            } else if (res.code.equals("PROTO_NOT_IMPL")) {
                code = TracerouteResult.PROT_UNREACHABLE;
            } else if (res.code.equals("NO_ROUTE_TO_HOST")) {
                code = TracerouteResult.NET_UNREACHABLE;
            } else if (res.code.equals("ADDRESS_UNREACH")) {
                code = TracerouteResult.HOST_UNREACHABLE;
            }
        } else {
            if (res.code.equals("GOT_REPLY_PACKET")) {
                code = TracerouteResult.DEST_REACHED_V6;
            } else if (res.code.equals("TTL_EXCEEDED")) {
                code = TracerouteResult.TTL_EXC_V6;
            } else if (res.code.equals("PORT_IS_CLOSED")) {
                code = TracerouteResult.DEST_REACHED_V6;
            } else if (res.code.equals("NO_ROUTE_TO_HOST")) {
                code = TracerouteResult.NOROUTE_HOST_V6;
            } else if (res.code.equals("ADDRESS_UNREACH")) {
                code = TracerouteResult.HOST_UNREACHABLE_V6;
            } else if (res.code.equals("ADMIN_FILTER")) {
                code = TracerouteResult.ADMIN_UNREACHABLE_V6;
            }
        }

        return new TracerouteResult(res.interfaceIp, res.ttl, res.rtt, code,
                               res.isIPv4 ? 0 : 1, res.reply.length());
    }

    public long createSocket(int s_port) {
        return Traceroute.createProber(true, s_port);
    }

    public TracerouteResult trace6(TracerouteParameters parameters,
                                   long prober) {
        return trace(parameters, prober);
    }

    public long createSocket6(int s_port) {
        return Traceroute.createProber(false, s_port);
    }

    public void closeSocket(long prober) {
        Traceroute.closeProber(prober);
    }

    public synchronized int[] getRandomSourcePort() {
        Random r = new Random();
        int i = r.nextInt(64511);
        while (!mPortFree[i]) {
            i = r.nextInt(64511);
        }
        mPortFree[i] = false;
        int[] ret = new int[2];
        ret[0] = i;
        ret[1] = mPorts[i];
        return ret;
    }

    public synchronized int[] getSourcePortByID(int id) {
        if (id < 0) {
            return null;
        }
        if (mPortFree[id]) {
            mPortFree[id] = false;
            int[] r = new int[2];
            r[0] = id;
            r[1] = mPorts[id];
            return r;
        }
        return null;
    }

    public synchronized void freePort(int id) {
        mPortFree[id] = true;
    }

    public synchronized void incProbeCount(int x) {
        mTotalProbesCount += x;
    }

    public synchronized int getProbeCount() {
        return mTotalProbesCount;
    }

    public synchronized void incICMPCount(int x) {
        mTotalICMPCount += x;
    }

    public synchronized int getICMPCount() {
        return mTotalICMPCount;
    }

    public synchronized void resetCount() {
        mTotalICMPCount = 0;
        mTotalProbesCount = 0;
    }
}
