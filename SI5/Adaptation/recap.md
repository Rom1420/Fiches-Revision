# LetThemCook — Récap de l’app (Angular)

## Vue d’ensemble
- App Angular en composants `standalone` + routing (`frontend/src/app/app.routes.ts`).
- Données en mémoire via un service singleton `OrderService` (mocks clonés au démarrage).
- Interaction principale: drag & drop (Angular CDK) pour assigner/réassigner et réordonner des commandes.

## Routing (pages)
Défini dans `frontend/src/app/app.routes.ts` :
- `/` → redirect `/chef/tablet`
- `/chef/tablet` → `ChefTabletComponent` (tableau chef)
- `/chef/priority` → `ChefPriorityComponent` (gestion des priorités)
- `/commis/screen` → `CommisScreenComponent` (vue globale commis)
- `/commis/tablet` → `CommisTabletComponent` (sélection d’un commis)
- `/commis/:id` → `CommisDetailComponent` (détail d’un commis)
- `**` → redirect `/chef/tablet`

## Composants (UI)
### Racine
- `frontend/src/app/app.ts` — `AppComponent`
  - Rôle: shell minimal, affiche uniquement le `RouterOutlet`.

### Chef
- `frontend/src/app/chef/chef-tablet/chef-tablet.component.ts` — `ChefTabletComponent`
  - Rôle: tableau “Nouvelles commandes” + colonnes par commis.
  - Utilise: `OrderListComponent` pour chaque colonne + `DetailsToggleComponent`.
  - Action clé: `onOrderDropped()` → `orderService.assign()` / `orderService.unassign()`.
  - Navigation auto: si une commande devient prioritaire, redirige vers `/chef/priority`.
  - Template: `frontend/src/app/chef/chef-tablet/chef-tablet.component.html`.

- `frontend/src/app/chef/chef-priority/chef-priority.component.ts` — `ChefPriorityComponent`
  - Rôle: colonne “Priorité” + “Nouvelles commandes” + colonnes commis.
  - Utilise: `OrderListComponent` + `DetailsToggleComponent`.
  - Action clé: `onOrderDropped()` → assign/unassign.
  - Auto-assign: bouton `Auto` appelle `orderService.autoAssignPriorities()` + popup countdown.
  - Template: `frontend/src/app/chef/chef-priority/chef-priority.component.html`.

### Commis
- `frontend/src/app/commis/commis-screen/commis-screen.component.ts` — `CommisScreenComponent`
  - Rôle: vue globale des commis (stats + listes par commis).
  - Utilise: `NavTabsComponent` + `OrderListComponent`.
  - Action clé: `onOrderDropped()` → `orderService.assign()` (réassigner entre commis).
  - Template: `frontend/src/app/commis/commis-screen/commis-screen.component.html`.

- `frontend/src/app/commis/commis-tablet/commis-tablet.component.ts` — `CommisTabletComponent`
  - Rôle: écran de sélection d’un commis (boutons + stats).
  - Utilise: `NavTabsComponent`.
  - Action clé: `selectCommis()` → navigate `/commis/:id`.
  - Template: `frontend/src/app/commis/commis-tablet/commis-tablet.component.html`.

- `frontend/src/app/commis/commis-detail/commis-detail.component.ts` — `CommisDetailComponent`
  - Rôle: détail d’un commis (liste de ses commandes) + actions.
  - Actions clés:
    - `start(order)` → `orderService.start(order.id)`
    - `finish(order)` → `orderService.finish(order.id)` (retire la commande)
    - `goBack()` → `/commis/tablet`
  - Timer d’affichage via `getTimer()`.
  - Template: `frontend/src/app/commis/commis-detail/commis-detail.component.html`.

### Composants partagés
- `frontend/src/app/order/order-list/order-list.component.ts` — `OrderListComponent`
  - Rôle: liste draggable/droppable.
  - Utilise: Angular CDK DragDrop (`cdkDropList`, `cdkDrag`).
  - 2 comportements:
    - Drop dans la même liste: réordonne uniquement les commandes “waiting” (non `startedAt`) puis appelle `orderService.reorderCommisOrders(listId, orderedIds)`.
    - Drop vers une autre liste: émet l’event au parent via `(dropped)`.
  - Template: `frontend/src/app/order/order-list/order-list.component.html`.

- `frontend/src/app/order/order-card/order-card.component.ts` — `OrderCardComponent`
  - Rôle: carte d’une commande (badge priorité/en-cours, timer, détails optionnels).
  - Timer: observable `interval(1000)` → `timer$` (affiché via `AsyncPipe`).
  - UX drag: clique émet `(activateDrag)` pour “armer” le drag côté `OrderListComponent`.
  - Template: `frontend/src/app/order/order-card/order-card.component.html`.

- `frontend/src/app/components/details-toggle/details-toggle.component.ts` — `DetailsToggleComponent`
  - Rôle: switch pour afficher/masquer les détails (`FormControl<boolean|null>`).
  - Template: `frontend/src/app/components/details-toggle/details-toggle.component.html`.

- `frontend/src/app/nav-tabs/nav-tabs.component.ts` — `NavTabsComponent`
  - Rôle: barre d’onglets/navigation (utilisée dans les vues commis; commentée côté chef dans les templates).
  - Template: `frontend/src/app/nav-tabs/nav-tabs.component.html` (actuellement vide).

## Données, modèles, mocks
- `frontend/src/models/order.model.ts`
  - `Order`: `id`, `label`, `createdAt`, `assignedTo/assignedAt`, `startedAt/finishedAt`, `priority`.
- `frontend/src/models/commis.model.ts`
  - `Commis`: `id`, `name`, `active`.

- `frontend/src/mocks/orders.mock.ts`
  - `COMMIS_MOCKS`: liste commis (avec `active`).
  - `ORDER_MOCKS`: commandes générées (non assignées, assignées, en cours).

## Service (logique métier / “state”)
- `frontend/src/services/order.service.ts` — `OrderService` (`providedIn: 'root'`)
  - Source de vérité: `BehaviorSubject<Order[]>` + `BehaviorSubject<Commis[]>`.
  - Initialise via clonage profond des mocks.
  - Tick interne `interval(1000)`:
    - Après 5 min sans assignation, une commande devient `priority = true`.
  - API utilisée par les composants:
    - `assign(orderId, commisId)`
    - `unassign(orderId)`
    - `start(orderId)`
    - `finish(orderId)` (supprime la commande du flux)
    - `autoAssignPriorities()` (répartit sur le commis le moins chargé)
    - `reorderCommisOrders(commisId|'unassigned', orderedIds)` (réordonnancement sans toucher aux `startedAt`)

## Flux typique “Chef drag & drop”
1. L’utilisateur drag une `OrderCard` dans une `OrderList` (CDK).
2. `OrderListComponent.handleDrop()`:
   - si cross-list → émet `(dropped)` au parent.
3. Parent (ChefTablet/ChefPriority) traite `onOrderDropped()`:
   - met à jour l’état via `OrderService.assign/unassign`.
4. Tous les écrans abonnés à `orderService.orders` se mettent à jour (dans le même onglet SPA).

