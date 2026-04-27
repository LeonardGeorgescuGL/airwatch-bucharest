import { useState } from 'react';
import { LandingPage } from './components/LandingPage';
import { MapInterface } from './components/MapInterface';
import { HistoricalDataInterface } from './components/HistoricalDataInterface';
import { CommunityAlertInterface } from './components/CommunityAlertInterface';
import { LeaderboardInterface } from './components/LeaderboardInterface';
import { LoginModal } from './components/LoginModal';
import { IntroAnimation } from './components/IntroAnimation';
import type { UserType, User } from './types';
import { api } from './api/api';

function App() {
  const [showIntro, setShowIntro] = useState(true);
  const [currentView, setCurrentView] = useState<'landing' | 'map' | 'history' | 'alerts' | 'leaderboard'>('landing');
  const [user, setUser] = useState<User>({ type: null, authenticated: false });
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [pendingView, setPendingView] = useState<'map' | 'history' | 'alerts' | 'leaderboard' | null>(null);

  const handleUserTypeSelect = (type: UserType) => {
    if (type === 'community-member') {
      setShowLoginModal(true);
    } else {
      setUser({ type, authenticated: true });
      setCurrentView('map');
    }
  };

  // Primeste datele complete de la backend dupa login reusit
  const handleLogin = (id: string, name: string, email: string, points: number, neighborhood: string) => {
    setUser({ type: 'community-member', id, name, email, points, neighborhood, authenticated: true });
    setShowLoginModal(false);
    if (pendingView) {
      setCurrentView(pendingView);
      setPendingView(null);
    } else {
      setCurrentView('map');
    }
  };

  const handleLogout = () => {
    api.logout().catch(() => {}); // Invalideaza sesiunea pe backend
    setUser({ type: null, authenticated: false });
    setCurrentView('landing');
    setPendingView(null);
  };

  const handleNavigation = (view: 'map' | 'history' | 'alerts' | 'leaderboard') => {
    if (view === 'alerts' && user.type !== 'community-member') {
      setPendingView(view);
      setShowLoginModal(true);
      return;
    }
    setCurrentView(view);
  };

  return (
    <div className="min-h-screen bg-slate-950">
      {showIntro && (
        <IntroAnimation onComplete={() => setShowIntro(false)} />
      )}
      
      {!showIntro && (
        <>
          {currentView === 'landing' && (
            <LandingPage onUserTypeSelect={handleUserTypeSelect} />
          )}
          
          {currentView === 'map' && user.authenticated && (
            <MapInterface 
              user={user} 
              onNavigate={handleNavigation}
              onLogout={handleLogout}
            />
          )}

          {currentView === 'history' && user.authenticated && (
            <HistoricalDataInterface 
              user={user} 
              onNavigate={handleNavigation}
              onLogout={handleLogout}
            />
          )}

          {currentView === 'alerts' && user.authenticated && user.type === 'community-member' && (
            <CommunityAlertInterface 
              user={user} 
              onNavigate={handleNavigation}
              onLogout={handleLogout}
            />
          )}

          {currentView === 'leaderboard' && user.authenticated && (
            <LeaderboardInterface 
              user={user} 
              onNavigate={handleNavigation}
              onLogout={handleLogout}
            />
          )}

          {showLoginModal && (
            <LoginModal 
              onLogin={handleLogin}
              onClose={() => {
                setShowLoginModal(false);
              }}
            />
          )}
        </>
      )}
    </div>
  );
}

export default App;