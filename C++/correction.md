# Révision DS

## Exercice 1 :

```c++
    class A { 
    public: 
        void finc (int n); 
        friend void fonc(int n);
    };
    void A::finc(int n){ cout << "finc"; }
    void A::fonc(int n){ cout << "fonc"; }
```
> _**Solution**_ :
> - **Fonction amie** : Peut accéder aux membres privés de la classe.
> - **Définition** : Ne doit pas utiliser le préfixe de portée (pas de ``A::`` ).
> - **Erreurs** : Utiliser ``A::`` avec une fonction amie entraîne des erreurs de compilation.


## Exercice 2 :

```c++
    class MVector{ 
    private:
        vector<double> v;
    public:
        MVector(double a, double b, double c) { v[0] = a; v[1] = b; v[2] = c };
    };
```
> _**Solution**_ :
> - **Initialisation du vecteur** : Au lieu d'utiliser v[0] = a; dans le constructeur, il est préférable d'utiliser la liste d'initialisation avec des accolades {a, b, c} pour créer et initialiser le vecteur avec les trois valeurs en une seule étape.
>   ```c++
>    class MVector{ 
>    private:
>        vector<double> v;
>    public:
>        MVector(double a, double b, double c) : v{a,b,c} {};
>    }; 
>```

## Exercice 3 :
```c++
    class B {
    private:
        int q;
    public:
        B(int n){ q=n; }
        int getq(){ return q; }
    };

    class A {
    private:
        int p;
    public:
        A(B b) { p = b.getq() + 1; }
        int getp() { return p; }
        operator B() { return B(2*p); }
        friend int fonc(A a);
    };
    int fonc(A a) { return a.p; }

    int main(){
        cout << B(2).getq() << A(B(2)).getp() <<
        B(A(B(2))).getq() << fonc(A(2)) << fonc(B(2)); 
    } 
```

> _**Que produit ce code**_ : 23633    

## Exercice 4 :

```c++
    class A
    {
        public:
            A() { cout << "0 ";}
            A(int k) { cout << "1 "; }
    };

    int main(){
        A a; 
        A b();
        A c(1);
        A d = 2;
    }
```

> _**Explication**_ :
>La déclaration ``A b()``; ne crée pas un objet, donc aucune sortie liée à b. On aurait du utiliser A a;
>``>A c(1);`` et ``A d = 2``; utilisent le constructeur qui prend un entier.

## Exerice 5 : 

> **Question** : Une fonction fonc amie d'une classe A doit etre déclarée à l'interieur du bloc correspondant à la classe A y a til une diff si l'on déclare fonc parmi les membres public ou private ? pourquoi ?

Non, il n'y a pas de différence si la fonction amie fonc est déclarée parmi les membres publics ou privés d'une classe A.

**Explication :**
Une fonction amie d'une classe a accès à tous les membres (publics, protégés et privés) de cette classe, peu importe où elle est déclarée (dans la section public, protected ou private).

**Placement** : 
La déclaration de la fonction amie est simplement une indication pour le compilateur qu'elle est "amie" de la classe, ce qui lui donne des droits d'accès spéciaux. Que cette déclaration soit placée parmi les membres publics ou privés ne change pas ses privilèges.

## Exerice 6 : 

```c++
    class A {
        int n;
    public:
        int getn() { return n;}
        int compute() const { return getn() + 2; }
    };
```
> **Explication :**
> Une méthode marquée const garantit qu'elle ne modifiera pas l'état interne de l'objet, c'est-à-dire qu'elle ne modifiera pas les membres de la classe.
> La méthode getn() n'est pas marquée const, donc le compilateur ne permet pas de l'appeler à partir d'une méthode const.

>   ```c++
>    class A {
>        int n;
>    public:
>        int getn() const { return n;}
>        int compute() const { return getn() + 2; }
>    };
>```
