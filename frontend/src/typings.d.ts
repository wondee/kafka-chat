/* SystemJS module definition */
declare var module: NodeModule;

declare module 'stompjs';
declare module 'sockjs-client';

interface NodeModule {
  id: string;
}
