/*
 * MIT License
 *
 * Copyright (c) 2017 - 2019 Felix Klauke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.d3adspace.actuarius.protocol;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public enum DNSOperationCode {

    QUERY(0x01),
    INVERSE_QUERY(0x01),
    STATUS(0x02),
    UNKNOWN(0x03),
    NOTIFY(0x04),
    UPDATE(0x05);

    private final byte codeId;

    DNSOperationCode(byte codeId) {
        this.codeId = codeId;
    }

    DNSOperationCode(int codeId) {
        this((byte) codeId);
    }

    public static DNSOperationCode getCodeViaId(int codeId) {
        for (DNSOperationCode dnsOperationCode : values()) {
            if (dnsOperationCode.getCodeId() == (byte) codeId) {
                return dnsOperationCode;
            }
        }

        return UNKNOWN;
    }

    public byte getCodeId() {
        return codeId;
    }
}
