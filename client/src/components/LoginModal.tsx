import React, { useState } from 'react';
import { motion, AnimatePresence } from 'motion/react';
import { X, Mail, Lock, User } from 'lucide-react';
import { api } from '../api/api';

interface LoginModalProps {
  onLogin: (id: string, name: string, email: string, points: number, neighborhood: string) => void;
  onClose: () => void;
}

export function LoginModal({ onLogin, onClose }: LoginModalProps) {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [mode, setMode] = useState<'login' | 'register'>('login');
  const [message, setMessage] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setMessage(null);
    setLoading(true);

    if (mode === 'register') {
      if (!name || !email || !password) {
        setError('Completează toate câmpurile pentru crearea contului.');
        setLoading(false);
        return;
      }
      try {
        await api.register({ name, email, passwordHash: password, role: 'USER' });
        setMessage('Cont creat cu succes. Te redirecționez către autentificare...');
        setTimeout(() => {
          setMode('login');
          setPassword('');
          setMessage(null);
        }, 900);
      } catch (err) {
        setError('Email deja folosit sau eroare la creare cont.');
      } finally {
        setLoading(false);
      }
      return;
    }

    // LOGIN — apel backend real
    if (!email || !password) {
      setError('Completează email și parolă pentru autentificare.');
      setLoading(false);
      return;
    }

    try {
      const userData = await api.login({ email, password });
      // userData vine de la backend: { id, name, email, role, points, neighborhood, passwordHash }
      onLogin(
        userData.id,
        userData.name,
        userData.email,
        userData.points ?? 0,
        userData.neighborhood ?? ''
      );
    } catch (err) {
      setError('Email sau parolă invalide.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <AnimatePresence>
      <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
        {/* Backdrop */}
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          onClick={onClose}
          className="absolute inset-0 bg-black/70 backdrop-blur-sm"
        />

        {/* Modal */}
        <motion.div
          initial={{ opacity: 0, scale: 0.9, y: 20 }}
          animate={{ opacity: 1, scale: 1, y: 0 }}
          exit={{ opacity: 0, scale: 0.9, y: 20 }}
          className="relative bg-gradient-to-br from-slate-900 to-slate-800 border border-purple-500/30 rounded-3xl p-8 max-w-md w-full shadow-2xl"
        >
          <button
            onClick={onClose}
            className="absolute top-6 right-6 text-slate-400 hover:text-white transition-colors"
          >
            <X className="w-6 h-6" />
          </button>

          <div className="text-center mb-6">
            <div className="inline-flex items-center justify-center w-16 h-16 bg-purple-500/20 rounded-2xl mb-4">
              <User className="w-8 h-8 text-purple-400" />
            </div>
            <h2 className="text-3xl text-white mb-2">Bun venit!</h2>
            <p className="text-slate-400">
              {mode === 'login' ? 'Autentifică-te ca membru al comunității' : 'Creează un cont de membru al comunității'}
            </p>
          </div>

          <div className="flex justify-center gap-4 mb-4">
            <button
              type="button"
              onClick={() => { setMode('login'); setError(null); setMessage(null); }}
              className={`px-4 py-2 rounded-full transition-colors ${mode === 'login' ? 'bg-purple-500 text-white' : 'bg-slate-700 text-slate-300'}`}
            >
              Autentificare
            </button>
            <button
              type="button"
              onClick={() => { setMode('register'); setError(null); setMessage(null); }}
              className={`px-4 py-2 rounded-full transition-colors ${mode === 'register' ? 'bg-purple-500 text-white' : 'bg-slate-700 text-slate-300'}`}
            >
              Creează cont
            </button>
          </div>

          <form onSubmit={handleSubmit} className="space-y-6">
            {message && <p className="text-center text-green-400">{message}</p>}
            {error && <p className="text-center text-rose-400">{error}</p>}

            {mode === 'register' && (
              <div>
                <label className="block text-slate-300 mb-2">Nume complet</label>
                <div className="relative">
                  <User className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-slate-500" />
                  <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="Ionescu Maria"
                    required
                    className="w-full bg-slate-800/50 border border-slate-700 rounded-xl py-3 pl-12 pr-4 text-white placeholder:text-slate-500 focus:outline-none focus:border-purple-500 transition-colors"
                  />
                </div>
              </div>
            )}

            <div>
              <label className="block text-slate-300 mb-2">Email</label>
              <div className="relative">
                <Mail className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-slate-500" />
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="email@exemplu.ro"
                  required
                  className="w-full bg-slate-800/50 border border-slate-700 rounded-xl py-3 pl-12 pr-4 text-white placeholder:text-slate-500 focus:outline-none focus:border-purple-500 transition-colors"
                />
              </div>
            </div>

            <div>
              <label className="block text-slate-300 mb-2">Parolă</label>
              <div className="relative">
                <Lock className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-slate-500" />
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="••••••••"
                  required
                  className="w-full bg-slate-800/50 border border-slate-700 rounded-xl py-3 pl-12 pr-4 text-white placeholder:text-slate-500 focus:outline-none focus:border-purple-500 transition-colors"
                />
              </div>
            </div>

            <motion.button
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              type="submit"
              disabled={loading}
              className="w-full bg-gradient-to-r from-purple-500 to-pink-500 hover:from-purple-600 hover:to-pink-600 text-white py-4 rounded-xl transition-all disabled:opacity-50"
            >
              {loading ? 'Se procesează...' : mode === 'login' ? 'Autentificare' : 'Creează cont'}
            </motion.button>
          </form>

          <p className="text-center text-slate-500 text-sm mt-6">
            Conectându-te, accepți să contribui la îmbunătățirea calității aerului în București
          </p>
        </motion.div>
      </div>
    </AnimatePresence>
  );
}
