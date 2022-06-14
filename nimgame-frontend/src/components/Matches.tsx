import styles from "./Matches.module.css";

type MatchesProps = {
  remainingMatches: number;
};

export function Matches(props: MatchesProps) {
  return (
    <div>
      {[...new Array(props.remainingMatches)].map((_, idx) => (
        <span key={`${idx}-match`} className={styles.Match}>
          🧨
        </span>
      ))}
    </div>
  );
}
