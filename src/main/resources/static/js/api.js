/* ================================================
   LibraryHub — API Utility
   ================================================ */

const API = {

    /** Legge il JWT da localStorage */
    getToken() {
        return localStorage.getItem('lh_token');
    },

    /** Redirect al login se il token è assente */
    requireAuth() {
        if (!this.getToken()) {
            window.location.href = '/login';
        }
    },

    /** Logout: cancella token e redirect */
    logout() {
        localStorage.removeItem('lh_token');
        window.location.href = '/login';
    },

    /** Headers standard con Authorization */
    headers(extra = {}) {
        return {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${this.getToken()}`,
            ...extra
        };
    },

    /** GET autenticato */
    async get(path) {
        const res = await fetch(path, { headers: this.headers() });
        if (res.status === 401) { this.logout(); return null; }
        if (!res.ok) { const e = await res.json().catch(() => ({})); throw new Error(e.message || `HTTP ${res.status}`); }
        return res.json();
    },

    /** POST autenticato */
    async post(path, body) {
        const res = await fetch(path, {
            method: 'POST',
            headers: this.headers(),
            body: JSON.stringify(body)
        });
        if (res.status === 401) { this.logout(); return null; }
        if (!res.ok) { const e = await res.json().catch(() => ({})); throw new Error(e.message || `HTTP ${res.status}`); }
        return res.json();
    },

    /** PATCH autenticato */
    async patch(path, body) {
        const res = await fetch(path, {
            method: 'PATCH',
            headers: this.headers(),
            body: JSON.stringify(body)
        });
        if (res.status === 401) { this.logout(); return null; }
        if (!res.ok) { const e = await res.json().catch(() => ({})); throw new Error(e.message || `HTTP ${res.status}`); }
        return res.json();
    },

    /** PUT autenticato */
    async put(path, body) {
        const res = await fetch(path, {
            method: 'PUT',
            headers: this.headers(),
            body: JSON.stringify(body)
        });
        if (res.status === 401) { this.logout(); return null; }
        if (!res.ok) { const e = await res.json().catch(() => ({})); throw new Error(e.message || `HTTP ${res.status}`); }
        return res.json();
    },

    /** DELETE autenticato */
    async delete(path) {
        const res = await fetch(path, {
            method: 'DELETE',
            headers: this.headers()
        });
        if (res.status === 401) { this.logout(); return null; }
        if (!res.ok) { const e = await res.json().catch(() => ({})); throw new Error(e.message || `HTTP ${res.status}`); }
        return true;
    },

    /** Decodifica il payload del JWT (base64) senza librerie */
    decodeToken() {
        try {
            const token   = this.getToken();
            if (!token) return null;
            const payload = token.split('.')[1];
            return JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));
        } catch {
            return null;
        }
    },

    /** Restituisce le iniziali dell'utente per l'avatar */
    userInitials() {
        const p = this.decodeToken();
        if (!p) return '?';
        // Il subject del JWT è l'email
        const email = p.sub || '';
        return email.charAt(0).toUpperCase();
    }
};